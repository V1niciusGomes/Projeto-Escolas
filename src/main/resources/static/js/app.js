// Updated parts of app.js: add edit/delete buttons and handlers for Ferramenta and Funcionario
const api = {
    funcionarios: '/api/funcionarios',
    emprestimos: '/api/emprestimos',
    ferramentas: '/api/ferramentas'
};

// Helper to show JSON nicely
function pretty(obj) {
    return JSON.stringify(obj, null, 2);
}

// Funcionarios page
async function loadFuncionarios() {
    const el = document.getElementById('funcionarios-list');
    try {
        const res = await fetch(api.funcionarios);
        if (!res.ok) throw res;
        const data = await res.json();
        el.textContent = '';
        if (Array.isArray(data)) {
            const ul = document.createElement('ul');
            data.forEach(f => {
                const li = document.createElement('li');
                li.textContent = `ID: ${f.id} - ${f.nome} (${f.matricula}) `;
                // edit button
                const editBtn = document.createElement('button');
                editBtn.textContent = 'Editar';
                editBtn.style.marginLeft = '8px';
                editBtn.addEventListener('click', () => editFuncionario(f));
                li.appendChild(editBtn);
                // delete button
                const delBtn = document.createElement('button');
                delBtn.textContent = 'Excluir';
                delBtn.style.marginLeft = '4px';
                delBtn.addEventListener('click', () => deleteFuncionario(f.id));
                li.appendChild(delBtn);
                ul.appendChild(li);
            });
            el.appendChild(ul);
        } else {
            el.textContent = pretty(data);
        }
    } catch (err) {
        el.textContent = 'Erro ao carregar funcionários';
        console.error(err);
    }
}

async function registerFuncionario(e) {
    e.preventDefault();
    const nome = document.getElementById('funcionario-nome').value;
    const matricula = document.getElementById('funcionario-matricula').value;
    try {
        const res = await fetch(api.funcionarios, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({ nome, matricula })
        });
        const data = await res.json();
        alert('Funcionário cadastrado: ' + pretty(data));
        document.getElementById('funcionario-form').reset();
        loadFuncionarios();
    } catch (err) {
        alert('Erro ao cadastrar funcionário');
        console.error(err);
    }
}

async function editFuncionario(f) {
    const novoNome = prompt('Nome:', f.nome);
    if (novoNome === null) return; // cancelled
    const novaMatricula = prompt('Matrícula:', f.matricula);
    if (novaMatricula === null) return;
    try {
        const res = await fetch(`${api.funcionarios}/${f.id}`, {
            method: 'PUT',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({ nome: novoNome, matricula: novaMatricula })
        });
        if (!res.ok) {
            const err = await res.json().catch(() => ({}));
            alert('Erro: ' + (err.message || res.statusText));
            return;
        }
        const data = await res.json();
        alert('Funcionário atualizado: ' + pretty(data));
        loadFuncionarios();
    } catch (err) {
        alert('Erro ao atualizar funcionário');
        console.error(err);
    }
}

async function deleteFuncionario(id) {
    if (!confirm('Confirma exclusão do funcionário #' + id + '?')) return;
    try {
        const res = await fetch(`${api.funcionarios}/${id}`, { method: 'DELETE' });
        if (res.status === 204) {
            alert('Funcionário excluído');
            loadFuncionarios();
        } else {
            const err = await res.json().catch(() => ({}));
            alert('Erro: ' + (err.message || res.statusText));
        }
    } catch (err) {
        alert('Erro ao excluir funcionário');
        console.error(err);
    }
}

// Ferramentas page
async function loadFerramentas() {
    const el = document.getElementById('ferramentas-list');
    try {
        const res = await fetch(api.ferramentas);
        if (!res.ok) throw res;
        const data = await res.json();
        el.textContent = '';
        if (Array.isArray(data)) {
            const ul = document.createElement('ul');
            data.forEach(f => {
                const li = document.createElement('li');
                li.textContent = `ID: ${f.id} - ${f.nome} `;
                // edit button
                const editBtn = document.createElement('button');
                editBtn.textContent = 'Editar';
                editBtn.style.marginLeft = '8px';
                editBtn.addEventListener('click', () => editFerramenta(f));
                li.appendChild(editBtn);
                // delete button
                const delBtn = document.createElement('button');
                delBtn.textContent = 'Excluir';
                delBtn.style.marginLeft = '4px';
                delBtn.addEventListener('click', () => deleteFerramenta(f.id));
                li.appendChild(delBtn);
                ul.appendChild(li);
            });
            el.appendChild(ul);
        } else {
            el.textContent = pretty(data);
        }
    } catch (err) {
        el.textContent = 'Erro ao carregar ferramentas (verifique se /api/ferramentas existe)';
        console.error(err);
    }
}

async function registerFerramenta(e) {
    e.preventDefault();
    const nome = document.getElementById('ferramenta-nome').value;
    const descricao = document.getElementById('ferramenta-descricao').value;
    try {
        const res = await fetch(api.ferramentas, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({ nome, descricao })
        });
        const data = await res.json();
        alert('Ferramenta cadastrada: ' + pretty(data));
        document.getElementById('ferramenta-form').reset();
        loadFerramentas();
    } catch (err) {
        alert('Erro ao cadastrar ferramenta');
        console.error(err);
    }
}

async function editFerramenta(f) {
    const novoNome = prompt('Nome:', f.nome);
    if (novoNome === null) return;
    const novaDescricao = prompt('Descrição:', f.descricao || '');
    if (novaDescricao === null) return;
    try {
        const res = await fetch(`${api.ferramentas}/${f.id}`, {
            method: 'PUT',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({ nome: novoNome, descricao: novaDescricao })
        });
        if (!res.ok) {
            const err = await res.json().catch(() => ({}));
            alert('Erro: ' + (err.message || res.statusText));
            return;
        }
        const data = await res.json();
        alert('Ferramenta atualizada: ' + pretty(data));
        loadFerramentas();
    } catch (err) {
        alert('Erro ao atualizar ferramenta');
        console.error(err);
    }
}

async function deleteFerramenta(id) {
    if (!confirm('Confirma exclusão da ferramenta #' + id + '?')) return;
    try {
        const res = await fetch(`${api.ferramentas}/${id}`, { method: 'DELETE' });
        if (res.status === 204) {
            alert('Ferramenta excluída');
            loadFerramentas();
        } else {
            const err = await res.json().catch(() => ({}));
            alert('Erro: ' + (err.message || res.statusText));
        }
    } catch (err) {
        alert('Erro ao excluir ferramenta');
        console.error(err);
    }
}

// Empréstimos page functions remain unchanged
async function verificarDisponibilidade(id) {
    const el = document.getElementById('disponibilidade-result');
    try {
        const res = await fetch(`${api.emprestimos}/disponivel/${id}`);
        const data = await res.json();
        el.textContent = 'Disponível: ' + data.disponivel;
    } catch (err) {
        el.textContent = 'Erro ao verificar disponibilidade';
        console.error(err);
    }
}

async function registrarEmprestimo(e) {
    e.preventDefault();
    const ferramentaId = Number(document.getElementById('emprestimo-ferramentaId').value);
    const funcionarioId = Number(document.getElementById('emprestimo-funcionarioId').value);
    try {
        const res = await fetch(`${api.emprestimos}/registrar`, {
            method: 'POST', headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({ ferramentaId, funcionarioId })
        });
        if (!res.ok) {
            const err = await res.json().catch(() => ({}));
            alert('Erro: ' + (err.message || res.statusText));
            return;
        }
        const data = await res.json();
        alert('Empréstimo registrado: ' + pretty(data));
        document.getElementById('emprestimo-form').reset();
    } catch (err) {
        alert('Erro ao registrar empréstimo');
        console.error(err);
    }
}

async function registrarDevolucao(e) {
    e.preventDefault();
    const id = Number(document.getElementById('devolucao-emprestimoId').value);
    try {
        const res = await fetch(`${api.emprestimos}/devolucao/${id}`, { method: 'POST' });
        const data = await res.json();
        alert('Devolução registrada: ' + pretty(data));
        document.getElementById('devolucao-form').reset();
    } catch (err) {
        alert('Erro ao registrar devolução');
        console.error(err);
    }
}

async function consultarHistorico(e) {
    e.preventDefault();
    const ferramentaId = document.getElementById('historico-ferramentaId').value;
    const funcionarioId = document.getElementById('historico-funcionarioId').value;
    const out = document.getElementById('historico-result');
    out.textContent = 'Carregando...';
    try {
        let res;
        if (ferramentaId) {
            res = await fetch(`${api.emprestimos}/historico/ferramenta/${ferramentaId}`);
        } else if (funcionarioId) {
            res = await fetch(`${api.emprestimos}/historico/funcionario/${funcionarioId}`);
        } else {
            out.textContent = 'Informe ferramentaId ou funcionarioId';
            return;
        }
        const data = await res.json();
        out.textContent = pretty(data);
    } catch (err) {
        out.textContent = 'Erro ao consultar histórico';
        console.error(err);
    }
}

// Attach event listeners when page loads
window.addEventListener('DOMContentLoaded', () => {
    if (document.getElementById('funcionarios-list')) {
        loadFuncionarios();
        document.getElementById('funcionario-form').addEventListener('submit', registerFuncionario);
    }
    if (document.getElementById('ferramentas-list')) {
        loadFerramentas();
        document.getElementById('ferramenta-form').addEventListener('submit', registerFerramenta);
    }
    if (document.getElementById('emprestimo-form')) {
        document.getElementById('emprestimo-form').addEventListener('submit', registrarEmprestimo);
        document.getElementById('verificar-form').addEventListener('submit', (e) => { e.preventDefault(); verificarDisponibilidade(document.getElementById('verificar-ferramentaId').value); });
        document.getElementById('devolucao-form').addEventListener('submit', registrarDevolucao);
        document.getElementById('historico-form').addEventListener('submit', consultarHistorico);
    }
});
