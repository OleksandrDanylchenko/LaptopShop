<#import "../../errorsHandlers/emptyFieldsBreaker.ftlh" as eB>
<#import "../../errorsHandlers/valueConsistentBreaker.ftlh" as vC>
<#import "../../modalInputManage/formStatusCleaner.ftlh" as fSc>

<#macro labelEdit id labelBrand labelModel>
    <div class="modal fade" id="labelEditModal${id}" data-backdrop="static" tabindex="-1"
         role="dialog" aria-labelledby="labelEditLabel${id}" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-title h3" id="labelEditLabel${id}">Змінити найменування</div>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form method="post" id="labelEditForm${id}" action="/label/edit/${id}"
                          class="form-group needs-validation w-100" novalidate onsubmit="return validateForm${id}()">
                        <div class="form-row mb-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="brand">Назва бренду:</label>
                                <input type="text" id="editBrandInput${id}" class="form-control" name="brand" required/>
                                <div class="invalid-feedback">Назву бренду задано некоректно!</div>
                            </div>
                        </div>
                        <div class="form-row my-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="model">Назва змінюваної моделі:</label>
                                <input type="text" id="editModelInput${id}" class="form-control" name="model" required/>
                                <div class="invalid-feedback">Назву змінюваної моделі задано некоректно!</div>
                            </div>
                        </div>
                    </form>
                    <@eB.emptyBreaker inputIds=["editBrandInput${id}", "editModelInput${id}"]/>
                    <@vC.valueBreaker id="${id}" inputIds=["editBrandInput${id}", "editModelInput${id}"]/>
                    <@fSc.fieldsStatusCleaner modalId="labelEditModal${id}" formId="labelEditForm${id}"/>

                    <!-- Restore initial value on opening of modal -->
                    <script>
                        $('#labelEditModal${id}').on('show.bs.modal', function () {
                            document.getElementById("editBrandInput${id}").value = "${labelBrand}";
                            document.getElementById("editModelInput${id}").value = "${labelModel}";
                        })
                    </script>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-dark" data-dismiss="modal">Скасувати</button>
                    <button type="submit" form="labelEditForm${id}" class="btn btn-danger">Змінити</button>
                </div>
            </div>
        </div>
    </div>
</#macro>