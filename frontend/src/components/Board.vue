<template>
<div id="game">
    <div id="player">Player: {{game.turn.replaceAll("_", " ")}} </div>
    <H1 id="winner" v-show="game.winner">
        Player: {{game.winner? game.winner.replaceAll("_", " ") : ''}} has won!
    </h1>
    <div class="container">
        <div class="row">
            <div class="col-sm-1 col-md-1 col-sm-3 col-xs-1 home-pit" style="margin-left: 12px; margin-right:-20px;">
                <div type="button" class="mancala">{{ firstPlayerHomePit[0].stonesCount }}</div>
                <!-- <div :key="index" v-for="index in firstPlayerHomePit[0].stonesCount" class="circle"></div> -->
            </div>
            <div :key="i" v-for="i in pitsCount" class="col-sm-1 col-md-1 col-sm-3 col-xs-1 mancala-col" style="padding:0px">
                <div class="short-div pit-cell">
                    <div type="button" class="pit-layer" @click="choosePit(firstPlayerPits[pitsCount.length - i - 1])">{{ firstPlayerPits[pitsCount.length - i - 1].stonesCount}}
                        <!-- <div :key="index" v-for="index in firstPlayerPits[pitsCount.length - i - 1].stonesCount " class="circle"></div> -->
                    </div>
                </div>
                <div class="short-div pit-cell">
                    <div type="button" class="pit-layer" @click="choosePit(secondPlayerPits[i])">{{ secondPlayerPits[i].stonesCount}}
                        <!-- <div :key="index" v-for="index in secondPlayerPits[i].stonesCount " class="circle"></div> -->
                    </div>

                </div>
            </div>
            <div class="col-sm-1 col-md-1 col-sm-3 col-xs-1 home-pit" style="margin-left: 20px;">
                <div type="button" class="mancala">{{ secondPlayerHomePit[0].stonesCount }}
                    <!-- <div :key="index" v-for="index in secondPlayerHomePit[0].stonesCount" class="circle"></div> -->
                </div>
            </div>
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
            http.put(`/api/games/${this.game.id}/pits/${pit.id}`)
                .then(response => {
                    this.game = response.data;
                })
                .catch(e => {
                    console.log(e);
                    alert(e.response.data)
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
            http.post("/api/games/")
                .then(response => {
                    this.game = response.data;
                    console.log(response);
                })
                .catch(e => {
                    console.log(e);
                    alert(e.response.data)
                });
        }
        /* eslint-enable no-console */
    }
};
</script>

<style scoped>
#winner{
    color: red;
}
.mancala,
.pit-layer {
    width: 100%;
    height: 100%;
    display: flex;
    padding: 15px;
    box-sizing: border-box;
    cursor: pointer;
    justify-content: space-around;
    align-items: center;
    flex-wrap: wrap;
    flex-direction: row;
    top: 0;
    left: 0;
    bottom: 0;
    right: 0;
    z-index: 2;
}

.circle {
    display: block;
    border-radius: 100% !important;
    height: 10px !important;
    width: 10px !important;
    margin: 0 !important;
    background: radial-gradient(circle at 10px 10px, #5cabff, #000) !important;
}

.mancala-col {
    margin: 0px 29px;
    right: 12px;
}

.mancala {
    height: 80%;
    width: 80%;
    padding: 15px;
    box-sizing: border-box;
    border-radius: 40px;
    background: linear-gradient(rgba(0, 0, 0, 0.5), rgba(0, 0, 0, 0.5)), url('../assets/wood.jpg');
    ;
    text-transform: uppercase;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-wrap: wrap;
    position: absolute;
    z-index: 2;
}

.container {
    background-image: url('../assets/wood.jpg');
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    border-radius: 20px;
}

.row {
    height: 100%;
    width: 100%;
}

.home-pit div {
    top: 10%;
}

.pit-cell div {
    top: 10%;
    border-radius: 50%;
    width: 115px;
    height: 115px;
    position: relative;
    margin: 10px;
    box-sizing: border-box;
    background: linear-gradient(rgba(0, 0, 0, 0.5), rgba(0, 0, 0, 0.5)), url('../assets/wood.jpg');
    padding: 15px;
    z-index: 1;
}

#player {
    float: left;
    position: fixed;
    top: 17%;
    left: 5%;
    font-weight: 700;
}

.pits-row {
    height: 50%;
    width: 100%;
}

.reverse {
    flex-flow: row-reverse;
}

#game {
    margin-top: 7%;
}
</style>
