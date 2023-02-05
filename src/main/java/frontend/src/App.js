import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import Sarasa2 from "./sarasa2";

function App() {
return (<Router><Routes>
    <Route path='/sarasa' element={<Sarasa2/>}/>
    <Route path='/sarasa2' element={<Script/>}/>
</Routes>
</Router>);}

export default App;

