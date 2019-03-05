package com.itmoshop.controllers;

import com.itmoshop.data.Account;
import com.itmoshop.data.Book;
import com.itmoshop.services.AdminService;
import com.itmoshop.services.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService admServ;
    @Autowired
    private DataService dataServ;

    @RequestMapping(method = RequestMethod.GET)
    public String admin(
            Model model) {
        try {
            model.addAttribute("webRootPathFolders", Arrays.asList(admServ.getWebRootPath().listFiles()));
        } catch (NullPointerException e) {
            model.addAttribute("webRootPathFolders", admServ.getWebRootPath().toString());
        }
        return "admin";
    }

    @RequestMapping(value = "/addSingle")
    public String addSingleBook(
            Model model,
            @ModelAttribute MultipartFile coverPicFile,
            @RequestParam String title,
            @RequestParam String publisher,
            @RequestParam String author,
            @RequestParam String isbn,
            @RequestParam String publishDate,
            @RequestParam String numOfPages,
            @RequestParam String language,
            @RequestParam String description,
            @RequestParam String price
    ) {
        Book bookToAdd = new Book();
        bookToAdd.setTitle(title);
        bookToAdd.setPublisher(publisher);
        bookToAdd.setAuthor(author);
        bookToAdd.setIsbn(isbn);
        bookToAdd.setPublishDate(publishDate);
        bookToAdd.setNumOfPages(Integer.parseInt(numOfPages));
        bookToAdd.setLanguage(language);
        bookToAdd.setDescription(description);
        bookToAdd.setPrice(Double.parseDouble(price));
        bookToAdd.setCoverPicFileName(coverPicFile.getOriginalFilename());

        admServ.addSingleBookImgToServer(coverPicFile);
        bookToAdd = dataServ.saveBook(bookToAdd);

        model.addAttribute("addedBookId", bookToAdd.getId());

        return "admin";
    }

    @RequestMapping(value = "/addMany", method = RequestMethod.POST)
    public String addManyBooks(
            Model model,
            @ModelAttribute MultipartFile zipArchive
    ) {
        List<Book> addedBooks = admServ.addArchiveContentsToServer(zipArchive);
        model.addAttribute("addedBooks", addedBooks);
        return "admin";
    }

    @RequestMapping("/account")
    public String inspectAccount(
            HttpServletRequest request,
            @RequestParam String userEmail
    ) {
        Account account = admServ.findAccountByEmail(userEmail);
        request.setAttribute("existingAccount", account);
        request.setAttribute("ordersSet", account.getOrdersSet());
        return "account";
    }

    @RequestMapping("/order")
    public String inspectOrder(
            @RequestParam long orderId
    ) {
        return "forward:/order/info?orderId=" + orderId;
    }

    @RequestMapping(value = "/dropAccounts", method = RequestMethod.POST)
    public String dropAccounts(Model model) {
        int numAccountsDeleted = admServ.deleteAllAccountsExceptAdmin();
        model.addAttribute("numAccountsDeleted", numAccountsDeleted);
        return "admin";
    }

    @RequestMapping(value = "/dropBooks", method = RequestMethod.POST)
    public String dropBooks(Model model) {
        int numBooksDeleted = admServ.deleteAllBooks();
        model.addAttribute("numBooksDeleted", numBooksDeleted);
        return "admin";
    }

    @RequestMapping(value = "/dropOrders", method = RequestMethod.POST)
    public String dropOrders(Model model) {
        int numOrdersDeleted = admServ.deleteAllItemOrders();
        model.addAttribute("numOrdersDeleted", numOrdersDeleted);
        return "admin";
    }


}
