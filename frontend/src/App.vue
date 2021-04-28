<template>
<div id="app" class="container-fluid">
    <div class="site-info">
        <img id="logo" src="./assets/logo.png" alt="Kalaha Game">
    </div>
    <nav>
        <board />
    </nav>
    <br />
    <router-view />
</div>
</template>

<script>
import Board from "./components/Board";

export default {
    name: "app",
    data() {
        return {};
    },
    components: {
        Board
    },
    methods: {},
};
</script>

<style>
.site-info {
    color: blue;
    margin-bottom: 20px;
}

#logo {
    width: 20%;
}

.container-fluid {
    text-align: center;
}
</style>
