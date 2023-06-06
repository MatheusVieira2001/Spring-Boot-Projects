package com.microservico.matheus.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservico.matheus.model.Produto;
import com.microservico.matheus.repositories.ProdutoRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/Product")
@Tag(name = "Products", description = "Api of Products")
public class ProdutoController {

	@Autowired
	private ProdutoRepository repository;

	
	@GetMapping("/List")
	@Operation(summary = "General product list", description = "All products")
	@ApiResponse(responseCode = "200", description = "Sucess", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))
	})
	public ResponseEntity<List<Produto>> getAllProdutos() {
		List<Produto> prodList = repository.findAll();
		if (!prodList.isEmpty()) {
			for (Produto produto : prodList) {
				Long id = produto.getId();
				produto.add(linkTo(methodOn(ProdutoController.class).getOneproduto(id)).withSelfRel());
			}
		}
		return new ResponseEntity<List<Produto>>(prodList, HttpStatus.OK);
	}

	
	
	@GetMapping("List/{id}")
	@Operation(summary = "Specific product search", description =  "Product by ID")
	@ApiResponse(responseCode = "200", description = "Sucess", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))
	})
	public ResponseEntity<Produto> getOneproduto(@PathVariable(value = "id") Long id) {
		Optional<Produto> produto = repository.findById(id);
		if (produto.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		produto.get().add(linkTo(methodOn(ProdutoController.class).getAllProdutos()).withRel("Product list"));
		return new ResponseEntity<Produto>(produto.get(), HttpStatus.OK);
	}

	@PostMapping("/Registration")
	@Operation(summary = "Product registration", description =  "name, category, description and price")
	@ApiResponse(responseCode = "201", description = "Successfully registered", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))
	})
	public ResponseEntity<Object> saveproduto(@RequestBody @Validated Produto Produto) {
		if(repository.existsByNome(Produto.getNome())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("product already registered, check the name!");
        }
		return new ResponseEntity<Object>(repository.save(Produto), HttpStatus.CREATED);
	}

	
	
	@DeleteMapping("/Delete/{id}")
	@Operation(summary = "Product removal", description =  "Submit Product ID")
	@ApiResponse(responseCode = "202", description = "Successfully deleted", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))
	})
	public ResponseEntity<?> deletePac(@PathVariable(value = "id") Long id) {
		Optional<Produto> produto = repository.findById(id);
		if (produto.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		repository.delete(produto.get());
		return new ResponseEntity<>(HttpStatus.OK);
	}

	
	
	@PutMapping("/Edition/{id}")
	@Operation(summary = "Product edition", description =  "Submit Product ID and name, category, description and price")
	@ApiResponse(responseCode = "201", description = "Successfully edited", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))
	})
	public ResponseEntity<?> updateProduct(@PathVariable(value = "id") Long id,
			@RequestBody @Validated Produto Produto) {
		Optional<Produto> produto = repository.findById(id);
		if (produto.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		Produto.setId(produto.get().getId());
		return new ResponseEntity<Produto>(repository.save(Produto), HttpStatus.OK);
	}

}
