<#import "../parts/htmlPad.ftlh" as pad>
<#import "../parts/errorOutput.ftlh" as error>

<@pad.page "Label editing">
    <@error.errorOutput errorMessage!/>

    <p class="h1">Змінити найменування:</p>
    <form method="post" action="/label/edit/${editLabel.id}" class="form-group needs-validation w-100" novalidate>
        <div class="form-row my-2">
            <div class="col">
                <label class="d-inline ml-2" for="brand">Назва бренду:</label>
                <input type="text" class="form-control" name="brand" value="${editLabel.brand!}" required/>
                <div class="invalid-feedback">Назва бренду не може бути пустою!</div>
            </div>
        </div>
        <div class="form-row my-2">
            <div class="col">
                <label class="d-inline ml-2" for="model">Назва моделі:</label>
                <input type="text" class="form-control" name="model" value="${editLabel.model!}" required/>
                <div class="invalid-feedback">Назва моделі не може бути пустою!</div>
            </div>
        </div>
        <button type="submit" class="btn btn-danger float-right">Зберегти</button>
    </form>
</@pad.page>