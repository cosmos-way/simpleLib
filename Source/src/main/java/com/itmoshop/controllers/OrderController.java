package com.itmoshop.controllers;

import com.itmoshop.data.Account;
import com.itmoshop.data.Book;
import com.itmoshop.data.ItemOrder;
import com.itmoshop.services.AdminService;
import com.itmoshop.services.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Controller
@RequestMapping("/order")
@SessionAttributes("currentSessionOrder")
public class OrderController {

    @Autowired
    private DataService dataServ;
    @Autowired
    private AdminService admServ;

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String getOrderInfo(
            @RequestParam long orderId,
            Model model) {
        ItemOrder oldInquiredOrder = admServ.findItemOrderById(orderId);
        Map<Book, Integer> orderedBooksMap = processItemOrderMap(oldInquiredOrder);
        model.addAttribute("oldInquiredOrder", oldInquiredOrder);
        model.addAttribute("orderedBooks", orderedBooksMap);
        return "order_info";
    }

    @RequestMapping(value = "/addItem", method = RequestMethod.POST)
    public String addItemToCart(
            @ModelAttribute("currentSessionOrder") ItemOrder currentSessionOrder,
            @RequestParam long bookId,
            @RequestParam int bookQuantity,
            @RequestParam String txtUrl,
            Principal principal,
            Model model) {
        if (bookQuantity <= 0)
            throw new IllegalArgumentException("Invalid bookQuantity parameter: " + bookQuantity);

        Account userAccount = admServ.findAccountByEmail(principal.getName());

        Iterator<ItemOrder> itemOrderIterator = userAccount.getOrdersSet().iterator();
        boolean match = false;
        while (itemOrderIterator.hasNext()) {
            ItemOrder next = itemOrderIterator.next();
            if (currentSessionOrder.equals(next)) {
                currentSessionOrder = next;
                match = true;
            }
        }

        if (!match) {
            currentSessionOrder = admServ.saveOrder(new ItemOrder());
            userAccount.addOrderToOrdersSet(currentSessionOrder);
        }

        Book bookToAdd = dataServ.findBookById(bookId);
        Map<Long, Integer> orderedBooks = currentSessionOrder.getOrderedBooks();

        Integer alreadyOrdered = orderedBooks.get(bookId);
        if (alreadyOrdered == null) {
            orderedBooks.put(bookId, bookQuantity);
        } else {
            orderedBooks.put(bookId, bookQuantity + alreadyOrdered);
        }

        currentSessionOrder.setOrderDateTime(new Date());
        currentSessionOrder.setOrderedBooks(orderedBooks);
        currentSessionOrder.addToOrderSum(bookToAdd.getPrice(), bookQuantity);

        admServ.saveAccount(userAccount);

        model.addAttribute("currentSessionOrder", currentSessionOrder);

        return "redirect:" + txtUrl;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteOrder(
            @ModelAttribute("currentSessionOrder") ItemOrder currentSessionOrder,
            @RequestParam long orderId,
            Principal principal,
            Model model
    ) {
        ItemOrder orderToDel = admServ.findItemOrderById(orderId);
        Account userAccount = admServ.findAccountByEmail(principal.getName());

        userAccount.removeOrderFromOrdersSet(orderToDel);
        admServ.saveAccount(userAccount);

        if (currentSessionOrder.getId() == orderToDel.getId()) {
            model.addAttribute("currentSessionOrder", new ItemOrder());
        }

        return "redirect:/account";
    }

    private Map<Book, Integer> processItemOrderMap(ItemOrder currentSessionOrder) {
        Map<Book, Integer> orderedBooksMap = new HashMap<>();
        Map<Long, Integer> orderedBookIdsMap = currentSessionOrder.getOrderedBooks();
        populateBooksMapByBookIds(orderedBooksMap, orderedBookIdsMap);
        return orderedBooksMap;
    }

    private void populateBooksMapByBookIds(Map<Book, Integer> orderedBooksMap, Map<Long, Integer> orderedBookIdsMap) {
        for (Map.Entry<Long, Integer> entry : orderedBookIdsMap.entrySet()) {
            Book bookObj = dataServ.findBookById(entry.getKey());
            Integer bookObjQty = entry.getValue();

            orderedBooksMap.put(bookObj, bookObjQty);
        }
    }
}
