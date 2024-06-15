package co.edu.co.spring.demo.controllers;

import co.edu.co.spring.demo.mapping.DTO.TransactionsDTO;
import co.edu.co.spring.demo.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionsController {

    @Autowired
    private TransactionsService transactionsService;

    @PostMapping
    public ResponseEntity<TransactionsDTO> createTransaction(@RequestBody TransactionsDTO transactionsDTO) {
        TransactionsDTO createdTransaction = transactionsService.createTransaction(transactionsDTO);
        return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionsDTO> getTransactionById(@PathVariable Integer id) {
        TransactionsDTO transaction = transactionsService.getTransactionById(id);
        return transaction != null ? ResponseEntity.ok(transaction) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<TransactionsDTO>> getAllTransactions() {
        List<TransactionsDTO> transactions = transactionsService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionsDTO> updateTransaction(@PathVariable Integer id, @RequestBody TransactionsDTO transactionsDTO) {
        TransactionsDTO updatedTransaction = transactionsService.updateTransaction(id, transactionsDTO);
        return updatedTransaction != null ? ResponseEntity.ok(updatedTransaction) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Integer id) {
        transactionsService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }
}
