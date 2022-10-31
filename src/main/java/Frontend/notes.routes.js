import  { Router } from "express";

let express = require('express');
const router = Router();
let app = express();
app.listen("https://prueba-tecnica-ensolvers.herokuapp.com/");
const nuevaNota =  (req, res) => res.render("notes/new-note");
const editarNota = (req, res) => res.render("notes/new-note");



app.get('/', function (req, res) {
    res.send('hello world')
})

// New Note
router.get("/notas/nueva", nuevaNota);

router.post("/notes/nueva", agregarNota);

// Get All Notes
router.get("/notes",renderNotes);

// Edit Notes
router.get("/notes/edit/:id", editarNota);

router.put("/notes/edit-note/:id",updateNote);

// Delete Notes
router.delete("/notes/delete/:id", deleteNote);

export default router;
