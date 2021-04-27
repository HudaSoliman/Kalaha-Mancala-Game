let AppConfig = {};

Object.defineProperties(AppConfig, {
  'apiBaseUrl': {
    value: "http://localhost:8080/" //4200
  },
  'vueBaseUrl': {
    value: "http://localhost:8080/"
  },
  'swaggerApiSpecification': {
    value: "http://localhost:4200/v2/api-docs?group=private-api"
  }
});

Object.freeze(AppConfig);
