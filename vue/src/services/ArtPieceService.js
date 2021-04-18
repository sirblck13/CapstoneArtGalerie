import axios from 'axios';

export default {

  getAllListings() {
    return axios.get('/home')
  },

  createListing(artPiece) {
    return axios.post('/createListing', artPiece)
  },

  getListingByArtId(artId){
    return axios.get(`/artDetails/${artId}`)
  },

  editListing(artPiece) {
    return axios.put('/edit', artPiece)
  },

  deleteListing(artID) {
    return axios.delete(`/delete/${artID}`)
  },

  updateOverrideCommission(artPiece){
    return axios.put('/overrideCommission', artPiece)
  },

  updateOverrideFee(artPiece){
    return axios.put('/overrideFee', artPiece)
  },

  updateFeesToDefault(artID){
    return axios.put(`/setDefault/${artID}`)
  },

  flipAvailability(artID) {
    return axios.put(`/availability/${artID}`);
  }

}