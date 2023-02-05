import React, {useState} from "react";

const Note = ({note}) => {
    const [isOpen, setIsOpen] = useState(false);

    const toggleOpen = () => {
        setIsOpen(true);
    };

    return (
        <div style={{
            display: "inline-block",
            width: "100px",
            height: "100px",
            border: "1px solid black",
            textAlign: "center",
            cursor: "pointer"
        }} onClick={toggleOpen}>
            {isOpen ? (
                <div>
                    <p>{note.title}</p>
                    <p>{note.content}</p>
                    {note.categories.map((category, index) => (
                        <div key={index}>
                            <p>{category.tag}</p>
                        </div>
                    ))}
                </div>
            ) : (
                <p>{note.title}</p>
            )}
        </div>
    );
};

export default Note;