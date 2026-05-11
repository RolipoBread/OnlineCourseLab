import "../../styles/auth.css";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { login } from "../../api/authApi";
import { useAuth } from "../../context/AuthContext";

export default function LoginPage() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const navigate = useNavigate();
    const { loginUser } = useAuth();

    const handleLogin = async () => {
        try {
            const res = await login({ email, password });

            const fullUser = res.data;

            loginUser(fullUser);

            navigate("/");
        } catch (e) {
            alert("Ошибка входа");
        }
    };
    return (
        <div className="auth-container">

            <div className="auth-box">

                <h1>Вход</h1>

                <input
                    className="auth-input"
                    placeholder="Эл. почта"
                    value={email}
                    onChange={e => setEmail(e.target.value)} />
                <input
                    className="auth-input"
                    type="password"
                    placeholder="Пароль"
                    value={password}
                    onChange={e => setPassword(e.target.value)}/>

                <button className="auth-btn" onClick={handleLogin}>Войти</button>

                <div className="auth-footer">
                    Нет аккаунта?
                    <a href="/register" className="auth-link">
                        Регистрация
                    </a>
                </div>

            </div>

        </div>
    );
}