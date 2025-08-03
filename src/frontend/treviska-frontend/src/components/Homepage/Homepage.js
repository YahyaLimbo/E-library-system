import React, { useRef } from 'react';
import { ChevronLeft, ChevronRight } from 'lucide-react';
import './Homepage.css';

const Homepage = () => {
    const newMaterialsRef = useRef(null);
    const popularMaterialsRef = useRef(null);
// make the buttons work
    const newMaterials = [
        { id: 1, title: "The Great Gatsby", author: "F. Scott Fitzgerald", cover: "/images/book1.jpg" },
        { id: 2, title: "To Kill a Mockingbird", author: "Harper Lee", cover: "/images/book2.jpg" },
        { id: 3, title: "1984", author: "George Orwell", cover: "/images/book3.jpg" },
        { id: 4, title: "Pride and Prejudice", author: "Jane Austen", cover: "/images/book4.jpg" },
        { id: 5, title: "The Catcher in the Rye", author: "J.D. Salinger", cover: "/images/book5.jpg" },
        { id: 6, title: "Lord of the Flies", author: "William Golding", cover: "/images/book6.jpg" },
        { id: 9, title: "Harry Potter", author: "J.K. Rowling", cover: "/images/book9.jpg" },
        { id: 10, title: "The Hobbit", author: "J.R.R. Tolkien", cover: "/images/book10.jpg" },
        { id: 11, title: "Dune", author: "Frank Herbert", cover: "/images/book11.jpg" },
        { id: 12, title: "The Da Vinci Code", author: "Dan Brown", cover: "/images/book12.jpg" },
        { id: 13, title: "Gone Girl", author: "Gillian Flynn", cover: "/images/book13.jpg" },
        { id: 14, title: "The Girl with the Dragon Tattoo", author: "Stieg Larsson", cover: "/images/book14.jpg" },
        { id: 15, title: "The Hunger Games", author: "Suzanne Collins", cover: "/images/book15.jpg" },
        { id: 16, title: "Twilight", author: "Stephenie Meyer", cover: "/images/book16.jpg" }
        
    ];

    const popularMaterials = [
        { id: 9, title: "Harry Potter", author: "J.K. Rowling", cover: "/images/book9.jpg" },
        { id: 10, title: "The Hobbit", author: "J.R.R. Tolkien", cover: "/images/book10.jpg" },
        { id: 11, title: "Dune", author: "Frank Herbert", cover: "/images/book11.jpg" },
        { id: 12, title: "The Da Vinci Code", author: "Dan Brown", cover: "/images/book12.jpg" },
        { id: 13, title: "Gone Girl", author: "Gillian Flynn", cover: "/images/book13.jpg" },
        { id: 14, title: "The Girl with the Dragon Tattoo", author: "Stieg Larsson", cover: "/images/book14.jpg" },
        { id: 15, title: "The Hunger Games", author: "Suzanne Collins", cover: "/images/book15.jpg" },
        { id: 16, title: "Twilight", author: "Stephenie Meyer", cover: "/images/book16.jpg" }
    ];

    const scroll = (ref, direction) => {
        const scrollAmount = 300;
        if (direction === 'left') {
            ref.current.scrollBy({ left: -scrollAmount, behavior: 'smooth' });
        } else {
            ref.current.scrollBy({ left: scrollAmount, behavior: 'smooth' });
        }
    };

    return (
        <div className="homepage">
            {/* New Releases Section */}
            <div className="material-sections">
                <h2 className="section-title">New Releases</h2>
                <div className="row-container">
                    <button 
                        className="nav-arrow nav-arrow-left"
                        onClick={() => scroll(newMaterialsRef, 'left')}
                    >
                        <ChevronLeft size={24} />
                    </button>
                    
                    <div className="materials-container" ref={newMaterialsRef}>
                        <div className="materials-row">
                            {newMaterials.map(item => (
                                <div key={item.id} className="material-card">
                                    <div className="material-cover">
                                        <img src={item.cover} alt={item.title} />
                                    </div>
                                    <div className="material-info">
                                        <h4 className="material-title">{item.title}</h4>
                                        <p className="material-author">{item.author}</p>
                                    </div>
                                </div>
                            ))}
                        </div>
                    </div>
                    
                    <button 
                        className="nav-arrow nav-arrow-right"
                        onClick={() => scroll(newMaterialsRef, 'right')}
                    >
                        <ChevronRight size={24} />
                    </button>
                </div>
            </div>
              
            {/* Popular Materials Section */}
            <div className="material-sections">
                <h2 className="section-title">Popular Materials</h2>
                <div className="row-container">
                    <button 
                        className="nav-arrow nav-arrow-left"
                        onClick={() => scroll(popularMaterialsRef, 'left')}
                    >
                        <ChevronLeft size={24} />
                    </button>
                    
                    <div className="materials-container" ref={popularMaterialsRef}>
                        <div className="materials-row">
                            {popularMaterials.map(item => (
                                <div key={item.id} className="material-card">
                                    <div className="material-cover">
                                        <img src={item.cover} alt={item.title} />
                                    </div>
                                    <div className="material-info">
                                        <h4 className="material-title">{item.title}</h4>
                                        <p className="material-author">{item.author}</p>
                                    </div>
                                </div>
                            ))}
                        </div>
                    </div>
                    
                    <button 
                        className="nav-arrow nav-arrow-right"
                        onClick={() => scroll(popularMaterialsRef, 'right')}
                    >
                        <ChevronRight size={24} />
                    </button>
                </div>
            </div>
        </div>
    );
};

export default Homepage;