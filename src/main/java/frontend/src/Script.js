import "./index.css"
import {useEffect, useState} from "react";
import axios from "axios";

const addBox = document.querySelector(".add-box"),
    popupBox = document.querySelector(".popup-box"),
    popupTitle = popupBox.querySelector("header p"),
    closeIcon = popupBox.querySelector("header i"),
    titleTag = popupBox.querySelector("input"),
    descTag = popupBox.querySelector("textarea"),
    addBtn = popupBox.querySelector("button");

const months = ["January", "February", "March", "April", "May", "June", "July",
    "August", "September", "October", "November", "December"];
const notes = JSON.parse(localStorage.getItem("notes") || "[]");
let isUpdate = false, updateId;
addBox.addEventListener("click", () => {
    popupTitle.innerText = "Add a new Note";
    addBtn.innerText = "Add Note";
    popupBox.classList.add("show");
    document.querySelector("body").style.overflow = "hidden";
    if(window.innerWidth > 660) titleTag.focus();
});
closeIcon.addEventListener("click", () => {
    isUpdate = false;
    titleTag.value = descTag.value = "";
    popupBox.classList.remove("show");
    document.querySelector("body").style.overflow = "auto";
});
let selectedTabId = 'active-notes';

    function showMenu(elem) {
        elem.parentElement.classList.add("show");
        document.addEventListener("click", e => {
            if(e.target.tagName !== "I" || e.target !== elem) {
                elem.parentElement.classList.remove("show");
            }
        });
    }
    function deleteNote(noteId) {
        let confirmDel = window.confirm("Are you sure you want to delete this note?");
        if(!confirmDel) return;
        notes.splice(noteId, 1);
        localStorage.setItem("notes", JSON.stringify(notes));
        Shownotes(selectedTabId);
    }
    function updateNote(noteId, title, filterDesc) {
        let description = filterDesc.replaceAll('<br/>', '\r\n');
        updateId = noteId;
        isUpdate = true;
        addBox.click();
        titleTag.value = title;
        descTag.value = description;
        popupTitle.innerText = "Update a Note";
        addBtn.innerText = "Update Note";
    }

const Shownotes = ({tabId}) => {
    const [notes, setNotes] = useState([]);
    useEffect(() => {

        let endpoint = tabId === "active-notes" ? "active" : "archived";
        axios.get(`https://localhost:8081/${endpoint}`, {
            auth: {
                username: "admin",
                password: "admin",
            }
        })
            .then(function (response) {
                setNotes(response.data);
            })

    }, [tabId]);

    return (
        <>
            <div className="tabs">
                <button id="active-notes" className="tab-button">Active</button>
                <button id="archived-notes" className="tab-button">Archived</button>
            </div>
            <ul>
                {notes
                    .map((note, index) => (
                        <li key={index} className="note">
                            <div className="details">
                                <p>{note.title}</p>
                                <span>{note.description.replace(/\n/g, '<br />')}</span>
                                {note.categories &&
                                    note.categories.map((category, index) => (
                                        <div key={index}>
                                            <p>{category.tag}</p>
                                        </div>
                                    ))}
                            </div>
                            <div className="bottom-content">
                                <span>{note.date}</span>
                                <div className="settings">
                                    <i onClick={() => showMenu(index)} className="uil uil-ellipsis-h"></i>
                                    <ul className="menu">
                                        <li onClick={() => updateNote(index, note.title, note.description)}>
                                            <i className="uil uil-pen"></i>Edit
                                        </li>
                                        <li onClick={() => deleteNote(index)}>
                                            <i className="uil uil-trash"></i>Delete
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </li>
                    ))}
            </ul>
        </>
    );
}

Shownotes(selectedTabId);
    export default Shownotes(selectedTabId)
addBtn.addEventListener("click", e => {
    e.preventDefault();
    let title = titleTag.value.trim(),
        description = descTag.value.trim();
    if(title || description) {
        let currentDate = new Date(),
            month = months[currentDate.getMonth()],
            day = currentDate.getDate(),
            year = currentDate.getFullYear();
        let noteInfo = {title, description, date: `${month} ${day}, ${year}`}
        if(!isUpdate) {
            notes.push(noteInfo);
        } else {
            isUpdate = false;
            notes[updateId] = noteInfo;
        }
        localStorage.setItem("notes", JSON.stringify(notes));
        Shownotes(selectedTabId);
        closeIcon.click();
    }
})

document.addEventListener("DOMContentLoaded", function() {
    const activeButton = document.querySelector("#active-notes");
    const archivedButton = document.querySelector("#archived-notes");

    activeButton.addEventListener("click", function() {
        activeButton.classList.add("active");
        archivedButton.classList.remove("active");
    });

    archivedButton.addEventListener("click", function() {
        archivedButton.classList.add("active");
        activeButton.classList.remove("active");
    });
});

