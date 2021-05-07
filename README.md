#KALAHA (Mancala) Game

### Built With
* Spring boot 2.4
* [Vue.js](https://vuejs.org/v2/guide/) - The Progressive JavaScript Framework used
* [Babel](https://babeljs.io/docs/en) - JavaScript compiler
* [Swagger.js](https://github.com/swagger-api/swagger-js) - Javascript library to connect to swagger-enabled APIs via browser or nodejs
* [ESLint](https://eslint.org/docs/user-guide/getting-started) - Pluggable linting utility for JavaScript
- Bootsrap


## Backend

To run backend

```
cd ./huda-soliman/
gradlew bootRun
```
the backend listens on port 8080

## Frontend

### Installing

After you have installed all necessary software, you can clone the project locally and move into the subfolder huda-soliman/frontend/`. In the subfolder execute `npm install` to get all dependencies which are defined in the `package.json`-file.

```
cd huda-soliman/frontend/
npm install
```

After all dependencies have been resolved, following commands can be executed:

```javascript
// Builds the frontend in development mode
npm run build

// Starts a development server with hot loading
npm run serve

```
It runs on http://localhost:4200


### TODO in Backend

- enhance testing and and add service tests and integration tests

### TODO in frontend

- Adding tests to vue components
- Adding state management with Vuex
- Enhance the UI (adding stones in the pits, replace the alert with dialog..etc)
- add translations 

