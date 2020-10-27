const history = require('connect-history-api-fallback')
const express = require('express')
const path    = require('path')

//indicnado folder para produção
const app = express()
const staticFileMiddleware = express.static(path.join(__dirname, 'dist'))

//utilizando modo history do vuejs
app.use(history())
app.use(staticFileMiddleware)

//indicando o index.html
app.get('/', function (req, res) {
    res.render(path.resolve(__dirname + '/../dist/index.html'))
})
//res.sendFile(path.resolve(__dirname + '/../dist/index.html'));



//inicializando o servidor express
var port = process.env.PORT || 4200
app.listen(port, function () {
    console.log( 'Express serving on 4200!' )
})








/*history = require('connect-history-api-fallback');
const express = require('express');
const path = require('path');
const serveStatic = require('serve-static');
app = express();
app.use(history());
app.use(serveStatic(__dirname + "/dist"));
var port = process.env.PORT || 4200;
app.listen(port);
console.log('server started '+ port);*/