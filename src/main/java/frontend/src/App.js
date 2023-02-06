import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import Script from "./Script"
import "./index.css"
import Sarasa from "./sarasa";

function App() {
return (<Router><Routes>
    <Route path='/sarasa2' element={<Script/>}/>
    <Route path='/sarasa3' element={<Sarasa/>}/>
</Routes>
</Router>);}

export default App;

