import React, { useState, useEffect } from "react";
import axios from "axios";

const Login = () => {
    const [notes, setNotes] = useState(null);

    useEffect(() => {
        axios.get('https://localhost:8081/notes/active', {
            redirect: 'follow',
            auth: {
                username: "admin",
                password: "admin",
            }
        })
            .then(response => {
                setNotes(JSON.stringify(response));
            })
            .catch(error => {
                console.error(error);
            });
    }, []);

    return (
        <div>
            {notes && (
                <div>
                    <h2>Notes</h2>
                    <pre>{notes}</pre>
                </div>
            )}
        </div>
    );
};

export default Login;

//DEJO ESTO ASI PORQUE FUNCIONA. PRUEBO EN OTROS ARCHIVOS.

