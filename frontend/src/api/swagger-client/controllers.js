import Swagger from 'swagger-client';
import AppConfig from '../../constants/AppConfig';

const swaggerClient = Swagger( "http://localhost:8080/v2/api-docs?group=private-api");

export default {
    Game: () => swaggerClient.then(client => client.apis["game-controller"])
};
