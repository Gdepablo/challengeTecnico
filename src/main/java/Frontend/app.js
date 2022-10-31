import express from "express";
import exphbs from "express-handlebars";
import { dirname, join } from "path";
import { fileURLToPath } from "url";

import notesRoutes from "./routes/notes.routes.js";
import "./config/passport.js";

// Initializations
const app = express();
const __dirname = dirname(fileURLToPath(import.meta.url));

// config view engine
const hbs = exphbs.create({
  defaultLayout: "main",
  layoutsDir: join(app.get("views"), "layouts"),
  partialsDir: join(app.get("views"), "partials"),
  extname: ".hbs",
});
app.engine(".hbs", hbs.engine);
app.set("view engine", ".hbs");

// routes
app.use(notesRoutes);

app.use(express.static(join(__dirname, "public")));

export default app;
