<template>
<div id="game">
    <div id="player">PLAYER: {{game.turn}} </div>
    <div id="board" class="d-flex flex-row mx-auto align-middle">

        <br />
        <button type="button" class="btn btn-outline-secondary pit invalid">{{ firstPlayerHomePit[0].stonesCount }}</button>
        <br />
        <div>
            <div class="btn-group me-1 pits-row reverse" role="group" aria-label="Player 2 pits">
                <button :key="i" type="button" :class="`btn btn-outline-secondary pit ${game.turn!==pit.owner || !firstPlayerPits[i].stonesCount ? 'invalid' : ''}`" v-for="(pit, i) in firstPlayerPits" @click="choosePit(pit)">{{ firstPlayerPits[i].stonesCount}}</button>
            </div>
            <br />
            <div class="btn-group me-1 pits-row" role="group" aria-label="Player 1 pits">
                <button :key="index" type="button" :class="`btn btn-outline-secondary pit ${game.turn!==p.owner || !secondPlayerPits[index].stonesCount ? 'invalid' : ''}`" v-for="(p, index) in secondPlayerPits" @click="choosePit(p)">{{secondPlayerPits[index].stonesCount}}</button>
            </div>
        </div>
        <button type="button" class="btn btn-outline-secondary pit invalid">{{ secondPlayerHomePit[0].stonesCount }}</button>
    </div>
    <div class="container">
        <div class="row">
            <div class="col-sm-1 col-md-1 col-sm-3 col-xs-1 home-pit" style="background-color:yellow"><span>{{ firstPlayerHomePit[0].stonesCount }}</span></div>
            <div :key="i" v-for="i in pitsCount" class="col-sm-1 col-md-1 col-sm-3 col-xs-1" style="padding:0px">
                <div class="short-div pit-cell" style="background-color:green"><span>{{ firstPlayerPits[pitsCount.length - i - 1].stonesCount}}</span></div>
                <div class="short-div pit-cell" style="background-color:purple"><span>{{ secondPlayerPits[i].stonesCount}}</span></div>
            </div>
            <div class="col-sm-1 col-md-1 col-sm-3 col-xs-1 home-pit" style="background-color:yellow"><span>{{ secondPlayerHomePit[0].stonesCount }}</span></div>
        </div>
    </div>
</div>
</template>

<script>
import http from "../http-common";
import Controllers from '../api/swagger-client/controllers';
import GameBoard from '../constants/GameBoard'

export default {
    name: "board",
    data() {
        return {
            game: GameBoard,
            pitsCount: [0, 1, 2, 3, 4, 5]

        };
    },
    props: ["customer"],
    mounted() {
        this.loadGame();
    },
    computed: {
        secondPlayerPits() {
            return this.game.pits.filter(p => p.owner === "SECOND_PLAYER" && !p.homePit);
        },
        firstPlayerPits() {
            return this.game.pits.filter(p => p.owner === "FIRST_PLAYER" && !p.homePit);
        },
        secondPlayerHomePit() {
            return this.game.pits.filter(p => p.owner === "SECOND_PLAYER" && p.homePit);
        },
        firstPlayerHomePit() {
            return this.game.pits.filter(p => p.owner === "FIRST_PLAYER" && p.homePit);
        }
    },
    methods: {
        choosePit(pit) {
            if (this.game.turn !== pit.owner) {
                this.invalidMove();
                return;
            }
            http.patch(`/api/games/${this.game.id}/pits/${pit.id}`)
                .then(response => {
                    this.game = response.data;
                })
                .catch(e => {
                    console.log(e);
                });

        },
        invalidMove() {
            alert("Invalid move!");
        },
        loadGame() {
            if (!this.game.length) {
                this.createGame();
            }
        },
        createGame() {
            let g = {};
            http.post("/api/")
                .then(response => {
                    this.game = response.data;
                    console.log(response);
                })
                .catch(e => {
                    console.log(e);
                });
        }
        /* eslint-enable no-console */
    }
};
</script>

<style scoped>
.container {
    height: 100px;
}

.row {
    height: 100%;
}

.home-pit {
    height: 100%;
    background-image: url('../assets/wood.jpg');
}

.home-pit span, .pit-cell span{
        top: 36%;
    position: relative;
}

.pit-cell {
    height: 50%;
    background-image: url('../assets/wood.jpg');
}

.invalid {
    cursor: not-allowed !important;
}

#player {
    float: left;
    position: fixed;
    top: 17%;
    left: 5%;
    font-weight: 700;
}

#board {
    margin: auto;
    width: 44%;
    padding: 11px;
}

.pits-row {
    height: 50%;
    width: 100%;
}

.reverse {
    flex-flow: row-reverse;
}
</style>
