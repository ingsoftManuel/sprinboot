package co.edu.co.spring.demo.service;

import co.edu.co.spring.demo.domain.model.Transaction;
import co.edu.co.spring.demo.mapping.DTO.TransactionsDTO;
import co.edu.co.spring.demo.mapping.mappers.TransactionsMapper;
import co.edu.co.spring.demo.repository.ReservationsRepository;
import co.edu.co.spring.demo.repository.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionsService {

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Autowired
    private ReservationsRepository reservationsRepository;

    public TransactionsDTO createTransaction(TransactionsDTO transactionsDTO) {
        Transaction transaction = TransactionsMapper.mapFromDTO(transactionsDTO, reservationsRepository);
        return saveAndMapTransaction(transaction);
    }

    public TransactionsDTO getTransactionById(Integer id) {
        return transactionsRepository.findById(id)
                .map(TransactionsMapper::mapFromModel)
                .orElse(null);
    }

    public List<TransactionsDTO> getAllTransactions() {
        return transactionsRepository.findAll().stream()
                .map(TransactionsMapper::mapFromModel)
                .collect(Collectors.toList());
    }

    public TransactionsDTO updateTransaction(Integer id, TransactionsDTO transactionsDTO) {
        return transactionsRepository.findById(id)
                .map(existingTransaction -> updateExistingTransaction(existingTransaction, transactionsDTO))
                .map(transactionsRepository::save)
                .map(TransactionsMapper::mapFromModel)
                .orElse(null);
    }

    public void deleteTransaction(Integer id) {
        transactionsRepository.deleteById(id);
    }

    private Transaction updateExistingTransaction(Transaction existingTransaction, TransactionsDTO transactionsDTO) {
        existingTransaction.setReservation(reservationsRepository.findById(transactionsDTO.reservationId())
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found")));
        existingTransaction.setAmount(existingTransaction.getReservation().getTotalCost());
        existingTransaction.setTransactionDate(Instant.now());
        existingTransaction.setPaymentMethod(transactionsDTO.paymentMethods());
        return existingTransaction;
    }

    private TransactionsDTO saveAndMapTransaction(Transaction transaction) {
        transaction = transactionsRepository.save(transaction);
        return TransactionsMapper.mapFromModel(transaction);
    }
}
