<#import "../parts/htmlPad.ftlh" as pad>
<#import "../parts/errorOutput.ftlh" as error>

<@pad.page "Label editing">
    <@error.errorOutput errorMessage!/>

    <p class="h1">Змінити найменування:</p>
    <form method="post" action="/label/edit/${editLabel.id}" class="form-group needs-validation w-100" novalidate>
        <div class="form-row">
            <input type="text" class="form-control mr-1 mt-2" name="brand" value="${editLabel.brand!}"
                   placeholder="Бренд" required/>
            <div class="invalid-feedback">Назва бренду не може бути пустою!</div>
        </div>
        <div class="form-row">
            <input type="text" class="form-control mr-1 mt-2" name="model" value="${editLabel.model!}"
                   placeholder="Модель" required/>
            <div class="invalid-feedback">Назва моделі не може бути пустою!</div>
        </div>
        <button type="submit" class="btn btn-primary my-2 float-right">Зберегти</button>
    </form>
</@pad.page>