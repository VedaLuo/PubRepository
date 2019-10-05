<template>
  <div>
    <h1>{{ msg }}</h1>
    <br/>table
    <button @click="doTest">test123</button> <button @click="doIt">doit{{txt}}</button>
    <br/>{{txt}}
    <br/><input v-model="txt"/>
  </div>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';

@Component
export default class SysTable extends Vue {
  @Prop() private msg!: string;
  @Prop() private txt!:string;


  private mounted() {
      this.txt="init111";
  }
  public doIt(){
    this.txt="333";
  }

  doTest() {
      this.txt="222";
      //alert(91);
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="less">

</style>
