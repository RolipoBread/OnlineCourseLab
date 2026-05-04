export default function Footer() {
    return (
        <footer className="footer">

            <div className="container footer-inner">

                <div className="footer-top">
                    <a href="/" className="footer-logo logo">
                        Lab
                    </a>

                    <div className="footer-contacts">
                        <a href="mailto:test@mail.com" className="footer-item">Эл. почта</a>
                        <a href="tel:+1234567890" className="footer-item">Телефон</a>
                        <a
                            href="https://t.me/yourtelegram"
                            target="_blank"
                            rel="noreferrer"
                            className="footer-item"
                        >
                            Телеграм
                        </a>
                    </div>
                </div>

                <div className="footer-disclaimer">
                    Материалы предоставлены исключительно в образовательных целях. <br />
                    Результаты обучения могут отличаться в зависимости от практики и усилий. <br />
                    Платформа не гарантирует трудоустройство или конкретные результаты.
                </div>

            </div>

        </footer>
    );
}