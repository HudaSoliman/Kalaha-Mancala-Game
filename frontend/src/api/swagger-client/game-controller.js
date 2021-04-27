import Controllers from './controllers';

export default {
    createGame: () => Controllers.Game().then(res => {
        return res.createGame({
            gameResource: newGame
        });
    }),
    getGameById: id => Controllers.Game().then(res => {
        return res.getGame({
            id: id
        });
    }),
    updateGame: Game => Controllers.Game().then(res => {
        return res.updateGame({
            id: Game.id,
            gameResource: Game
        });
    }),
    deleteGame: (id) => Controllers.Game().then((res) => {
        return res.deleteGame({
            id: id
        });
    })
}
