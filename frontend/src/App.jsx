import { Routes, Route } from "react-router-dom";

import MainLayout from "./components/layout/MainLayout";

import HomePage from "./pages/public/HomePage";
import LoginPage from "./pages/auth/LoginPage";
import RegisterPage from "./pages/auth/RegisterPage";
import ProfilePage from "./pages/profile/ProfilePage";
import CoursePage from "./pages/course/CoursePage";

export default function App() {
    return (
        <Routes>

            {/* PUBLIC LAYOUT */}
            <Route element={<MainLayout />}>
                <Route path="/" element={<HomePage />} />
                <Route path="/profile" element={<ProfilePage />} />
                <Route path="/courses/:id" element={<CoursePage />} />
            </Route>

            {/* AUTH */}
            <Route path="/login" element={<LoginPage />} />
            <Route path="/register" element={<RegisterPage />} />


        </Routes>
    );
}