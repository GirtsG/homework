package io.fourfinanceit.homework.controller;

import io.fourfinanceit.homework.entity.LoanExtension;
import io.fourfinanceit.homework.service.LoanExtensionService;
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
public class LoanExtensionController {

    private final LoanExtensionService service;

    @PostMapping(path = "/extend-loan")
    public @ResponseBody
    ResponseEntity<?> extendLoan(@Validated @RequestBody LoanExtensionResource resource) {
        LoanExtension loanExtension = new LoanExtension();
        loanExtension.setTermInDays(resource.getTermInDays());
        LoanExtension content = service.extendLoan(resource);
        List<Link> links = new ArrayList<>();
        links.add(linkTo(methodOn(LoanExtensionController.class).extendLoan(resource))
                .withSelfRel()
                .withMedia(MediaTypes.HAL_JSON_UTF8_VALUE)
                .withTitle("Extend existing Loan by Loan Id")
        );
        links.add(linkTo(methodOn(LoanApplicationController.class).applyForLoan(new LoanApplicationResource()))
                .withRel("apply")
                .withMedia(MediaTypes.HAL_JSON_UTF8_VALUE)
                .withTitle("Apply for New Loan"));
        return ResponseEntity.ok(new Resource<>(content, links));
    }
}
