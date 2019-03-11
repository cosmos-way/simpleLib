package com.itmoshop.controllers;

import com.google.gson.JsonObject;
import com.itmoshop.data.*;
import com.itmoshop.services.AdminService;
import com.itmoshop.services.DataService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.*;





@Controller
@RequestMapping("/bookRequest")
@SessionAttributes("currentSessionRequest")
public class RequestController {

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
            @RequestParam String txtUrl,
            Principal principal,
            Model model) {
        if (bookId <= 0)
            throw new IllegalArgumentException("Invalid bookId parameter: ");

        Account userAccount = admServ.findAccountByEmail(principal.getName());
        Book bookToAdd = dataServ.findBookById(bookId);

        BookRequest req = new BookRequest();
//        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        req.setOrderDateTime(new Date(System.currentTimeMillis()));
        req.setBook(bookToAdd);
        req.setAccount(userAccount);
        req.setStatus(RequestStatus.ORDERED);
        req.setDateTill(new Date(System.currentTimeMillis()+24*24*3600));

        admServ.saveRequest(req);
//        java.sql.Timestamp.valueOf()
//        req.setOrderDateTime();

//        ItemOrder ord = new ItemOrder();
//        admServ.saveOrder()


//        Iterator<ItemOrder> itemOrderIterator = userAccount.getOrdersSet().iterator();
//        boolean match = false;
//        while (itemOrderIterator.hasNext()) {
//            ItemOrder next = itemOrderIterator.next();
//            if (currentSessionOrder.equals(next)) {
//                currentSessionOrder = next;
//                match = true;
//            }
//        }
//
//        if (!match) {
//            currentSessionOrder = admServ.saveOrder(new ItemOrder());
//            userAccount.addOrderToOrdersSet(currentSessionOrder);
//        }


//        Map<Long, Integer> orderedBooks = currentSessionOrder.getOrderedBooks();
//
//        Integer alreadyOrdered = orderedBooks.get(bookId);
//        if (alreadyOrdered == null) {
//            orderedBooks.put(bookId, bookQuantity);
//        } else {
//            orderedBooks.put(bookId, bookQuantity + alreadyOrdered);
//        }
//
//        currentSessionOrder.setOrderDateTime(new Date());
//        currentSessionOrder.setOrderedBooks(orderedBooks);
//        currentSessionOrder.addToOrderSum(bookToAdd.getPrice(), bookQuantity);

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

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String getAllRequest(
            @RequestParam long len,
            Model model, Principal principal) {
        String email = principal.getName();
        Account account = admServ.findAccountByEmail(email);
        List<BookRequest> requests = account.getRequests();
        ArrayList<BookRequest> newRequests = new ArrayList<>(requests);

        for(BookRequest req:newRequests)
        {
            System.out.println(req);
        }
        model.addAttribute("reqestedBooks", newRequests);
        return "allRequests";
    }

//
    @RequestMapping(value = "/requestSaveStatus", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    ResponseEntity<?> requestSaveStatus  (
            @RequestBody BookRequest reqData)  {
        BookRequest req = admServ.findRequestById(reqData.getId());
        req.setStatus(reqData.getStatus());
        admServ.saveRequest(req);


        return ResponseEntity.noContent().build();
    }

}


