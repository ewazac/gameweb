const history = require('connect-history-api-fallback')
const express = require('express')
const path    = require('path')
const cors = require('cors')

const app = express()
const staticFileMiddleware = express.static(path.join(__dirname, 'dist'))

app.use(history())
app.use(staticFileMiddleware)
app.use(cors({
    origin: "gameweb.projektstudencki.pl",
    credentials: true
}))
app.use(cors({
    origin: "gameweb12.herokuapp.com",
    credentials: false
}))
app.use(cors({
    origin: "gameweb.projektstudencki.pl/api",
    credentials: false
}))

app.get('/', function (req, res) {
    res.render(path.resolve(__dirname + '/../dist/index.html'))
})

var port = process.env.PORT || 4200
app.listen(port, function () {
    console.log( 'Express serving on 4200!' )
})