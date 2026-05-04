import "../../styles/auth.css";

export default function RegisterPage() {
    return (
        <div className="auth-container">

            <div className="auth-box">

                <h1>Регистрация</h1>

                <input className="auth-input" placeholder="Имя" />
                <input className="auth-input" placeholder="Эл. почта" />
                <input className="auth-input" type="password" placeholder="Пароль" />

                <button className="auth-btn">Создать аккаунт</button>

                <div className="auth-footer">
                    Уже есть аккаунт?
                    <a href="/login" className="auth-link">
                        Войти
                    </a>
                </div>

            </div>

        </div>
    );
}