import React, { useState, useEffect } from "react";
import axios from "axios";
import Note from "./Note";

const Login = () => {
    const [notes, setNotes] = useState(null);

    useEffect(() => {
        axios
            .get("https://localhost:8081/notes/active", {
                redirect: "follow",
                auth: {
                    username: "admin",
                    password: "admin",
                },
            })
            .then((response) => {
                if (response.data) {
                    setNotes(response.data);
                }
            })
            .catch((error) => {
                console.error(error);
            });
    }, []);

    return (
        <div>
            {notes ? (
                notes.map((note, index) => (
                    <Note key={index} note={note} />
                ))
            ) : (
                <div>Loading...</div>
            )}
        </div>
    );
};

export default Login;
