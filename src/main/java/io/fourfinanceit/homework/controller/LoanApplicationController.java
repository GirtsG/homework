package io.fourfinanceit.homework.controller;

import io.fourfinanceit.homework.entity.LoanApplication;
import io.fourfinanceit.homework.service.LoanApplicationService;
import io.fourfinanceit.homework.value.LoanApplicationResource;
import io.fourfinanceit.homework.value.LoanExtensionResource;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
public class LoanApplicationController {

    private final LoanApplicationService service;

    @PostMapping(path = "/apply-for-loan")
    public @ResponseBody
    ResponseEntity<Resource<LoanApplication>> applyForLoan(@Validated @RequestBody LoanApplicationResource resource) {
        LoanApplication content = service.createLoanApplication(resource);
        List<Link> links = new ArrayList<>();
        links.add(linkTo(methodOn(LoanApplicationController.class).applyForLoan(resource))
                .withSelfRel()
                .withMedia(MediaTypes.HAL_JSON_UTF8_VALUE)
                .withTitle("Apply for New Loan")
        );
        links.add(linkTo(methodOn(LoanExtensionController.class).extendLoan(new LoanExtensionResource()))
                .withRel("extend")
                .withMedia(MediaTypes.HAL_JSON_UTF8_VALUE)
                .withTitle("Extend existing Loan by Loan Id"));
        return ResponseEntity.ok(new Resource<>(content, links));
    }
}
