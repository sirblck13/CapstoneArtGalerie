<template>
    <tr>
        <td>{{getTitle}}</td>
        <td>{{getStatus}}</td>
    </tr>
  
</template>

<script>
export default {
name: "listing-status",
props: ["artPiece"],

computed: {

    getStatus() {
        let status = "";

        if((this.$store.state.user.authorities[0].name == "ROLE_ARTIST")
          || (this.$store.state.user.authorities[0].name == "ROLE_SELLER")){
            if(this.artPiece.artist === this.$store.state.user.username){
                if(this.artPiece.sold){
                    status = "sold";
                }
                if(this.artPiece.available){
                    status = "available";
                }
                if( !this.artPiece.available && !this.artPiece.sold){
                    status = "unavailable";
                }
            }
        }

        if(this.$store.state.user.authorities[0].name == 'ROLE_DEALER'){
            if(this.artPiece.dealer === this.$store.state.user.username){
                if(this.artPiece.sold){
                    status = "sold";
                }
                if(this.artPiece.available){
                    status = "available";
                }
                if( !this.artPiece.available && !this.artPiece.sold){
                    status = "unavailable";
                }
            }
        }

        if(this.$store.state.user.authorities[0].name == 'ROLE_ADMIN'){
                if(this.artPiece.sold){
                    status = "sold";
                }
                if(this.artPiece.available){
                    status = "available";
                }
                if( !this.artPiece.available && !this.artPiece.sold){
                    status = "unavailable";
                }
        }

        return status;
    },

    getTitle(){

        let title = "";

        if((this.$store.state.user.authorities[0].name == "ROLE_ARTIST")
          || (this.$store.state.user.authorities[0].name == "ROLE_SELLER")){
            if(this.artPiece.artist === this.$store.state.user.username){
                title = this.artPiece.title;
                }
            }
        
        if(this.$store.state.user.authorities[0].name == 'ROLE_DEALER'){
            if(this.artPiece.dealer === this.$store.state.user.username){
                title = this.artPiece.title;
                }
            }
        
        if(this.$store.state.user.authorities[0].name == 'ROLE_ADMIN'){
                title = this.artPiece.title;
                }
            
        return title;
    }
}

};
</script>

<style>

</style>