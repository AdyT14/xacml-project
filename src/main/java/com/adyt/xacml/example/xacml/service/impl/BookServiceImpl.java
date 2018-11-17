package com.adyt.xacml.example.xacml.service.impl;

import com.adyt.xacml.example.xacml.domain.Book;
import com.adyt.xacml.example.xacml.domain.model.RequestModel;
import com.adyt.xacml.example.xacml.repository.BookRepository;
import com.adyt.xacml.example.xacml.service.BookService;
import com.adyt.xacml.example.xacml.xacml.Constants;
import com.adyt.xacml.example.xacml.xacml.XacmlParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.wso2.balana.PDP;
import org.wso2.balana.ParsingException;
import org.wso2.balana.ctx.AbstractResult;
import org.wso2.balana.ctx.ResponseCtx;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
}
