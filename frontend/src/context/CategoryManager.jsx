import { useEffect, useState } from "react";
import { getCategories } from "../../src/api/categoryApi.js";
import "../../src/styles/global.css";

export default function CategoryManager() {

    const [categories, setCategories] = useState([]);
    const [newName, setNewName] = useState("");

    const [editingId, setEditingId] = useState(null);
    const [editingName, setEditingName] = useState("");

    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const load = async () => {
        setLoading(true);
        setError(null);

        try {
            const res = await getCategories();
            setCategories(res.data || []);
        } catch (e) {
            console.error(e);
            setError("Ошибка загрузки категорий");
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        load();
    }, []);

    // ================= CREATE =================
    const createCategory = async () => {
        if (!newName.trim()) return;

        try {
            await fetch("http://localhost:8080/categories", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    name: newName.trim(),
                    description: "Описание категории"
                })
            });

            setNewName("");
            load();

        } catch (e) {
            console.error(e);
            setError("Ошибка создания категории");
        }
    };

    // ================= DELETE =================
    const deleteCategory = async (id) => {
        if (!window.confirm("Удалить категорию?")) return;

        try {
            await fetch(`http://localhost:8080/categories/${id}`, {
                method: "DELETE"
            });

            load();

        } catch (e) {
            console.error(e);
            setError("Ошибка удаления категории");
        }
    };

    // ================= START EDIT =================
    const startEdit = (cat) => {
        setEditingId(cat.id);
        setEditingName(cat.name);
    };

    // ================= CANCEL EDIT =================
    const cancelEdit = () => {
        setEditingId(null);
        setEditingName("");
    };

    // ================= SAVE EDIT =================
    const saveEdit = async (id) => {
        if (!editingName.trim()) return;

        try {
            await fetch(`http://localhost:8080/categories/${id}`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    name: editingName.trim(),
                    description: "Описание категории"
                })
            });

            cancelEdit();
            load();

        } catch (e) {
            console.error(e);
            setError("Ошибка обновления категории");
        }
    };

    return (
        <div className="admin-section">

            <h3>Категории</h3>

            {error && (
                <div style={{ color: "red", marginBottom: "10px" }}>
                    {error}
                </div>
            )}

            {/* CREATE */}
            <div style={{ display: "flex", gap: "10px", marginBottom: "15px" }}>

                <input
                    value={newName}
                    onChange={(e) => setNewName(e.target.value)}
                    placeholder="Новая категория"
                />

                <button onClick={createCategory}>
                    Добавить
                </button>

            </div>

            {loading && <div>Загрузка...</div>}

            {!loading && categories.length === 0 && (
                <div>Категорий нет</div>
            )}

            {/* LIST */}
            {categories.map(c => (
                <div
                    key={c.id}
                    style={{
                        display: "flex",
                        justifyContent: "space-between",
                        alignItems: "center",
                        padding: "10px",
                        background: "#111827",
                        marginBottom: "6px",
                        borderRadius: "8px"
                    }}
                >

                    {/* EDIT MODE */}
                    {editingId === c.id ? (
                        <input
                            value={editingName}
                            onChange={(e) => setEditingName(e.target.value)}
                            style={{ flex: 1, marginRight: "10px" }}
                        />
                    ) : (
                        <span>{c.name}</span>
                    )}

                    {/* ACTIONS */}
                    <div style={{ display: "flex", gap: "8px" }}>

                        {editingId === c.id ? (
                            <>
                                <button onClick={() => saveEdit(c.id)}>
                                    💾
                                </button>

                                <button onClick={cancelEdit}>
                                    ❌
                                </button>
                            </>
                        ) : (
                            <>
                                <button onClick={() => startEdit(c)}>
                                    ✏
                                </button>

                                <button
                                    onClick={() => deleteCategory(c.id)}
                                    style={{ color: "red" }}
                                >
                                    🗑
                                </button>
                            </>
                        )}

                    </div>

                </div>
            ))}

        </div>
    );
}