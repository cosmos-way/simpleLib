package com.itmoshop.controllers;

import com.itmoshop.data.Book;
import com.itmoshop.data.ItemOrder;
import com.itmoshop.services.AdminService;
import com.itmoshop.services.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/checkout")
@SessionAttributes("currentSessionOrder")
public class CheckoutController {

    @Autowired
    private DataService dataServ;

    @Autowired
    private AdminService admServ;

    @RequestMapping(method = RequestMethod.GET)
    public void showOrderCart(
            @ModelAttribute("currentSessionOrder") ItemOrder itemOrder,
            Model model) {
        Map<Book, Integer> orderedBooksMap = processItemOrderMap(itemOrder);
        model.addAttribute("orderedBooks", orderedBooksMap);
    }

    @RequestMapping(value = "/delItem", method = RequestMethod.POST)
    public String deleteItemFromOrder(
            @ModelAttribute("currentSessionOrder") ItemOrder currentSessionOrder,
            @RequestParam("bookId") long bookId,
            Model model) {
        removeBookFromItemOrder(currentSessionOrder, bookId);
        admServ.saveOrder(currentSessionOrder);
        Map<Book, Integer> orderedBooksMap = processItemOrderMap(currentSessionOrder);
        model.addAttribute("orderedBooks", orderedBooksMap);

        return "checkout";
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    public String confirmOrder(
            @ModelAttribute("currentSessionOrder") ItemOrder currentSessionOrder,
            Model model
    ) {
        currentSessionOrder.setConfirmedOrder(true);
        currentSessionOrder.setNewOrder(false);
        admServ.saveOrder(currentSessionOrder);
        model.addAttribute("currentSessionOrder", new ItemOrder());
        model.addAttribute("confirmed", true);
        return "checkout";
    }

    @RequestMapping(value = "/empty", method = RequestMethod.POST)
    public String emptyCart(
            @ModelAttribute("currentSessionOrder") ItemOrder currentSessionOrder,
            @RequestParam("txtUrl") String textUrl,
            Model model) {
        ItemOrder newOrder = new ItemOrder();
        newOrder.setId(currentSessionOrder.getId());
        admServ.saveOrder(newOrder);
        model.addAttribute("currentSessionOrder", newOrder);
        return "redirect:/";
    }

    private void removeBookFromItemOrder(ItemOrder currentSessionOrder, long bookId) {
        double remBookPrice = dataServ.findBookById(bookId).getPrice();
        Map<Long, Integer> orderedBookIdsMap = currentSessionOrder.getOrderedBooks();
        int remBookQty = orderedBookIdsMap.remove(bookId);
        currentSessionOrder.subtractFromOrderSum(remBookPrice, remBookQty);
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
