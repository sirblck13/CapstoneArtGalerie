package com.techelevator.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import com.techelevator.dao.ArtPieceDAO;
import com.techelevator.model.ArtPiece;

@PreAuthorize("isAuthenticated()")
@RestController
@CrossOrigin

public class ArtPieceController {

	private ArtPieceDAO artDAO;
	
	
	
	public ArtPieceController(ArtPieceDAO artDAO) {
		this.artDAO = artDAO;
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_DEALER') or hasRole('ROLE_ARTIST') or hasRole('ROLE_SELLER')")
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(path = "/createListing", method=RequestMethod.POST)
	public void createListing( @RequestBody ArtPiece art){
		artDAO.createListing(art);
	}
	
	@PreAuthorize("permitAll")
	@RequestMapping(path = "/home", method=RequestMethod.GET)
	public List<ArtPiece> getAllListings(){
		return artDAO.getAllListings();
	}
	
	
	@RequestMapping(path = "/artDetails/{artId}", method=RequestMethod.GET)
	public ArtPiece getListingByArtID(@PathVariable int artId) {
		
		return artDAO.getListingByArtID(artId);
	}
	

	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_DEALER') or hasRole('ROLE_ARTIST') or hasRole('ROLE_SELLER')")
	@RequestMapping(path="/edit", method=RequestMethod.PUT)
	public void updateArtPiece(@RequestBody ArtPiece art) {
		
		artDAO.updateArtPiece(art);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_DEALER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(path="/delete/{id}", method=RequestMethod.DELETE)
	public void deleteArtPiece(@PathVariable int id) {
		
		artDAO.deleteArtPiece(id);
	}
    
	@PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path="/overrideFee", method=RequestMethod.PUT)
	public void updateOverrideFee(@RequestBody ArtPiece art) {
		artDAO.overrideFee(art);
	}
    
	@PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path="/overrideCommission", method=RequestMethod.PUT)
	public void updateOverrideCommission(@RequestBody ArtPiece art) {
    	artDAO.overrideCommision(art);
	}
    
	@PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path="/setDefault/{id}", method=RequestMethod.PUT)
	public void updateFeesToDefault(@PathVariable int id) {
    	artDAO.setFeesToDefault(id);
    }
    
	@PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path="/availability/{id}", method=RequestMethod.PUT)
	public void flipAvailable(@PathVariable int id) {
		artDAO.flipAvailable(id);
	}
    
}
