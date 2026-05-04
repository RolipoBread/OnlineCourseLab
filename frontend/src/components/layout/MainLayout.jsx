import { Outlet } from "react-router-dom";
import Header from "./Header";
import Footer from "./Footer";

export default function MainLayout() {
    return (
        <div className="app-layout">

            <Header />

            <main className="container app-content">
                <Outlet />
            </main>

            <Footer />

        </div>
    );
}