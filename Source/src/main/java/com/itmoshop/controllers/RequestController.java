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

        if(admServ.findActiveRequest(userAccount.getId(), bookToAdd.getId()))
            throw new IllegalArgumentException("Подобный запрос существует");


        BookRequest req = new BookRequest();
//        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        req.setOrderDateTime(new Date(System.currentTimeMillis()));
        req.setBook(bookToAdd);
        req.setAccount(userAccount);
        req.setStatus(RequestStatus.ORDERED);

        Date tillDate = new Date(System.currentTimeMillis());
        Calendar c = Calendar.getInstance();
        c.setTime(tillDate);
        c.add(Calendar.DATE, 14);
        tillDate = c.getTime();


        req.setDateTill(tillDate);

        admServ.saveRequest(req);

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


    @RequestMapping(value = "/myRequests", method = RequestMethod.GET)
    public String myRequests(Model model, Principal principal) {

        String email = principal.getName();
        Account account = admServ.findAccountByEmail(email);
        List<BookRequest> requests = account.getRequests();
        ArrayList<BookRequest> newRequests = new ArrayList<>(requests);
        model.addAttribute("reqestedBooks", newRequests);
        return "allRequests";
    }
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String getAllRequest(
            @RequestParam(defaultValue = "10") long limit,
            @RequestParam(defaultValue = "null") String status,
            @RequestParam(defaultValue = "-1") long id,
            @RequestParam(defaultValue = "-1") long userId,
            @RequestParam(defaultValue = "-1") long bookId,
            @RequestParam(defaultValue = "null") String dateFrom,
            @RequestParam(defaultValue = "null") String dateTill,
            @RequestParam(defaultValue = "0") int expired,
            Model model, Principal principal) {

//        String email = principal.getName();
//        Account account = admServ.findAccountByEmail(email);
//        List<BookRequest> requests = account.getRequests();
//        ArrayList<BookRequest> newRequests = new ArrayList<>(requests);

        List<BookRequest> allRequests = admServ.findRequests(limit, status, id, userId, bookId, dateFrom, dateTill, expired);

        model.addAttribute("reqestedBooks", allRequests);
        return "allRequests";
    }


    @RequestMapping(value = "/requestSaveStatus", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    ResponseEntity<?> requestSaveStatus  (
            @RequestBody BookRequest reqData)  {
        BookRequest req = admServ.findRequestById(reqData.getId());
        req.setStatus(reqData.getStatus());
        admServ.saveRequest(req);


        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/requestSaveDateTill", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    ResponseEntity<?> requestSaveDateTill  (
            @RequestBody BookRequest reqData)  {

        BookRequest req = admServ.findRequestById(reqData.getId());
        req.setDateTill(reqData.getDateTill());
        admServ.saveRequest(req);

        return ResponseEntity.noContent().build();
    }

}


