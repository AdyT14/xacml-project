package com.adyt.xacml.example.xacml.service.impl;

import com.adyt.xacml.example.xacml.domain.Book;
import com.adyt.xacml.example.xacml.domain.model.RequestModel;
import com.adyt.xacml.example.xacml.repository.BookRepository;
import com.adyt.xacml.example.xacml.service.BookService;
import com.adyt.xacml.example.xacml.service.dto.BookDTO;
import com.adyt.xacml.example.xacml.xacml.Constants;
import com.adyt.xacml.example.xacml.xacml.XacmlParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.wso2.balana.PDP;
import org.wso2.balana.ParsingException;
import org.wso2.balana.ctx.AbstractResult;
import org.wso2.balana.ctx.Attribute;
import org.wso2.balana.ctx.ResponseCtx;
import org.wso2.balana.ctx.xacml3.Result;
import org.wso2.balana.xacml3.Attributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final PDP pdp;
    private final BookRepository bookRepository;

    public BookServiceImpl(PDP pdp, BookRepository bookRepository) {
        this.pdp = pdp;
        this.bookRepository = bookRepository;
    }

    @Override
    public ResponseEntity<List<Book>> getBooks(HttpServletRequest request) {
        RequestModel requestModel = new RequestModel();
        requestModel.setAction(Constants.ACTION_READ);
        requestModel.setResource(Constants.RESOURCE_BOOK);
        requestModel.setSubjectId(Long.valueOf(request.getHeader("userId")));

        String response = pdp.evaluate(XacmlParser.createXACMLRequest(requestModel));

        try {
            ResponseCtx responseCtx = ResponseCtx.getInstance(XacmlParser.getXacmlResponse(response));
            AbstractResult result = responseCtx.getResults().iterator().next();
            if (AbstractResult.DECISION_PERMIT == result.getDecision()) {
                return new ResponseEntity<>(bookRepository.findAll(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } catch (ParsingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> getBooksByField(HttpServletRequest request) {
        RequestModel requestModel = new RequestModel();
        requestModel.setAction(Constants.ACTION_READ);
        requestModel.setResource(Constants.RESOURCE_BOOK_BY_FIELD);
        requestModel.setSubjectId(Long.valueOf(request.getHeader("userId")));
        requestModel.setFields(Book.getFields());

        String response = pdp.evaluate(XacmlParser.createXACMLRequest(requestModel));
        Set<String> resultFieldsAllowed = new HashSet<>();
        try {
            ResponseCtx responseCtx = ResponseCtx.getInstance(XacmlParser.getXacmlResponse(response));
            Set<AbstractResult> results = responseCtx.getResults();
            for (AbstractResult result : results) {
                if (AbstractResult.DECISION_PERMIT == result.getDecision()) {
                    Set<Attributes> attributesSet = ((Result) result).getAttributes();
                    for (Attributes attributes : attributesSet) {
                        for (Attribute attribute : attributes.getAttributes()) {
                            resultFieldsAllowed.add(attribute.getValue().encode());
                        }
                    }
                }
            }
        } catch (ParsingException e) {
            throw new RuntimeException(e);
        }
        if (resultFieldsAllowed.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(mapBooks(bookRepository.findAll(), resultFieldsAllowed), HttpStatus.OK);
    }

    private List<BookDTO> mapBooks(List<Book> books, Set<String> resultFieldsAllowed) {
        return books.stream().map(b -> {
            BookDTO bookDto = new BookDTO();
            if (resultFieldsAllowed.contains("id")) {
                bookDto.setId(b.getId());
            }
            if (resultFieldsAllowed.contains("description")) {
                bookDto.setDescription(b.getDescription());
            }
            if (resultFieldsAllowed.contains("price")) {
                bookDto.setPrice(b.getPrice());
            }
            if (resultFieldsAllowed.contains("title")) {
                bookDto.setTitle(b.getTitle());
            }
            return bookDto;
        }).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<?> getAllBooks() {
        return new ResponseEntity<>(bookRepository.findAll(), HttpStatus.OK);
    }
}
