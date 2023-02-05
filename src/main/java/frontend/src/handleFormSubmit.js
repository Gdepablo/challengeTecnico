import React, { useState, useEffect } from "react";
import axios from "axios";

const handleFormSubmit = (event) => {
    event.preventDefault();

    const formData = {
        title: event.target.elements.title.value,
        content: event.target.elements.content.value,
        categories: event.target.elements.categories.value
    };

    axios.post('https://localhost:8081/notes/new', formData, {
            auth: {
                username: "admin",
                password: "admin",
            }
    })
        .then((response) => {
            console.log(response);
        })
        .catch((error) => {
            console.error(error);
        });
};

export default handleFormSubmit;
