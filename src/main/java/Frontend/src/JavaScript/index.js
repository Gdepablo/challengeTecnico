import { BrowserRouter as Router, Route } from "react-router-dom";
import Home from "./Home";


const App = () => (
    <Router>
        <div>
            <Route exact path="/users" component={Home} />
            <Route path="/about" component={About} />
            <Route path="/contact" component={Contact} />
        </div>
    </Router>
);