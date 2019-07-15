package io.fourfinanceit.homework.controller;

import io.fourfinanceit.homework.value.LoanApplicationResource;
import io.fourfinanceit.homework.value.LoanExtensionResource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.h2.H2ConsoleProperties;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class RootController {

    private final RepositoryRestMvcConfiguration repositoryRestMvcConfiguration;
    private final H2ConsoleProperties h2ConsoleProperties;

    @GetMapping
    public ResponseEntity<ResourceSupport> index() {
        String currentUrl = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
        ResourceSupport resourceSupport = new ResourceSupport();
        ServletUriComponentsBuilder.fromCurrentContextPath();
        resourceSupport.add(linkTo(methodOn(RootController.class).index()).withSelfRel().withTitle("Application root"));
        resourceSupport.add(
                new Link(currentUrl + repositoryRestMvcConfiguration.baseUri().getUri(), "data")
                        .withTitle("Data Repositories root / HAL browser root")
        );
        resourceSupport.add(
                new Link(currentUrl + h2ConsoleProperties.getPath())
                        .withRel(h2ConsoleProperties.getPath())
                        .withTitle("H2 Database console")
        );
        resourceSupport.add(linkTo(methodOn(LoanApplicationController.class).applyForLoan(new LoanApplicationResource()))
                .withRel("apply")
                .withMedia(MediaTypes.HAL_JSON_UTF8_VALUE)
                .withTitle("Apply for New Loan")
        );
        resourceSupport.add(linkTo(methodOn(LoanExtensionController.class).extendLoan(new LoanExtensionResource()))
                .withRel("extend")
                .withMedia(MediaTypes.HAL_JSON_UTF8_VALUE)
                .withTitle("Extend existing Loan by Loan Id")
        );
        return ResponseEntity.ok(resourceSupport);
    }
}
