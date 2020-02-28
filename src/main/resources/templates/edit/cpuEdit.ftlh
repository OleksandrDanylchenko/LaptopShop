<#import "../parts/htmlPad.ftlh" as pad>
<#import "../parts/errorOutput.ftlh" as error>

<@pad.page "CPU editing">
    <@error.errorOutput errorMessage!/>

    <p class="h1">Змінити інформацію про процесор:</p>
    <form method="post" action="/cpu/edit/${editCpu.id}" class="form-group needs-validation w-100" novalidate>
        <div class="form-row">
            <input type="text" class="form-control mr-1 mt-2" name="model"
                   value="${editCpu.model!}" placeholder="Модель" required/>
            <div class="invalid-feedback">Назва моделі не може бути пустою!</div>
        </div>
        <div class="form-row">
            <input type="number" step="0.1" min="0.1" class="form-control mr-1 mt-2" name="frequency"
                   value="${editCpu.frequency!}" placeholder="Частота" required/>
            <div class="invalid-feedback">Некоректна частота!</div>
        </div>
        <button type="submit" class="btn btn-primary float-right">Зберегти</button>
    </form>
</@pad.page>