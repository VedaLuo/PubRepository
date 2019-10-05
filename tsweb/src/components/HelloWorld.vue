<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
    <br/>TS
  </div>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';

@Component
export default class HelloWorld extends Vue {
  @Prop() private msg!: string;

  private  mounted() {
      
  }

  public greet(): string {
      // alert('greeting: ' + this.msg);
      return '';
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="less">

</style>
