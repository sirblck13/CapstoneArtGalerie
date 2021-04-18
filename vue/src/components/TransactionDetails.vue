<template>
  <div>
    <div
      class="container"
      v-if="$store.state.user.username == transaction.artist"
    >
      <div class="portfolio-item-wrapper">
        <div class="portfolio-img-background">
          <img
            class="transactionImg"
            :src="imgFile"
            v-bind:alt="transaction.imgFileName"
          />
          <div
            class="portfolio-img-background"
            v-bind:style="{ 'background-image': 'url(' + imgFile + ')' }"
          ></div>
        </div>
        <div class="img-text-wrapper">
          <div class="subtitle">
            <div id="art-title">
              {{ transaction.title }}
            </div>
            <div id="t-price">Payment Received: ${{ transaction.price }}</div>
            <div id="dateSold">Date Sold: {{ transaction.dateSold }}</div>
          </div>
        </div>
      </div>
    </div>
    <div
      class="container"
      v-if="$store.state.user.username == transaction.dealer"
    >
      <div class="portfolio-item-wrapper">
        <div class="portfolio-img-background">
          <img
            class="transactionImg"
            :src="imgFile"
            v-bind:alt="transaction.imgFileName"
          />
          <div
            class="portfolio-img-background"
            v-bind:style="{ 'background-image': 'url(' + imgFile + ')' }"
          ></div>
        </div>
        <div class="img-text-wrapper">
          <div class="subtitle">
            <div id="art-title">
              {{ transaction.title }} - {{ transaction.artist }}
            </div>
            <div id="t-price">Price: ${{ transaction.price }}</div>
            <div class="t-com">
              Commission Received: ${{ transaction.commission }}
            </div>
            <div id="dateSold">Date Sold: {{ transaction.dateSold }}</div>
          </div>
        </div>
      </div>
    </div>
    <div
      class="container"
      v-if="$store.state.user.authorities[0].name == 'ROLE_ADMIN'"
    >
      <div class="portfolio-item-wrapper">
        <div class="portfolio-img-background">
          <img
            class="transactionImg"
            :src="imgFile"
            v-bind:alt="transaction.imgFileName"
          />
          <div
            class="portfolio-img-background"
            v-bind:style="{ 'background-image': 'url(' + imgFile + ')' }"
          ></div>
        </div>
        <div class="img-text-wrapper">
          <div class="subtitle">
            <div id="art-title">
              {{ transaction.title }} - {{ transaction.artist }}
            </div>
            <div id="t-price">Revenue: ${{ transaction.totalPrice }}</div>
            <div class="t-com">
              Commission Paid: ${{ transaction.commission }}
            </div>
            <div id="t-price">Paid to Artist: ${{ transaction.price }}</div>
            <div id="dateSold">Date Sold: {{ transaction.dateSold }}</div>
            <div id="t-fee">Net: ${{ transaction.fee }}</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import firebase from "firebase";
export default {
  name: "transaction-details",
  props: ["transaction"],
  data() {
    return {
      imgFile: {},
    };
  },
  created() {
    let storage = firebase.storage();
    let storageRef = storage.ref();
    let imgRef = storageRef.child(this.transaction.imgFileName);
    imgRef.getDownloadURL().then((url) => {
      this.imgFile = url;
    });
  },
};
</script>
<style>
.portfolio-item-wrapper {
  position: relative;
}
/* .portfolio-item-wrapper {
    display: grid;
    grid-template-columns: 1fr 1fr 1fr;
    grid-template-areas:
        "picture picture picture"
        "picture picture picture"
        "picture picture picture"
        "picture picture picture";
} */
/* .portfolio-img-background {
    height: 350px;
    width: 100%;
    background-size: cover;
    background-position: center;
    background-repeat: no-repeat;
} */
/* .container {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    text-align: center;
} */
.container {
  background-color: #ab3f2969;
  border-width: 3px;
  /* box-shadow: 2px 2px 2px 2px #ab3f2985; */
  border-color: #AB3F29;
  /* border-radius: 5px; */
  top: 95px;
  width: 300px;
  height: auto;
  padding: 0px;
  margin: 10px;
  display: flex;
  flex-direction: column;
  color: #f4f4f4eb;
  font-family: "Quicksand", sans-serif;
}
.img-text-wrapper {
  /* background-color: rgba(250, 235, 215, 0.228); */
  background-size: contain;
  background-repeat: no-repeat;
  background-position: center;
  width: 300px;
  height: 100%;
  position: absolute;
  top: 0;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
  padding-left: 20%;
  padding-right: 15%;
}
.transactionImg {
  width: 100%;
  height: auto;
}
.img-text-wrapper .subtitle {
  transition: 0.5s;
  font-weight: 600;
  font-size: 1em;
  color: transparent;
  top: 10%;
}
.img-text-wrapper:hover .subtitle {
  font-weight: 600;
  color: rgb(228, 172, 104);
}
.img-text-wrapper:hover {
  transition: 0.5s;
  font-weight: 600;
  color: transparent;
  /* padding-top: 50%; */
  background-color: #0c2d09cd;
}
</style>





















