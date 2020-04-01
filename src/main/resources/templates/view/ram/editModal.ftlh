<#import "../../errorsHandlers/emptyFieldsBreaker.ftlh" as eB>
<#import "../../errorsHandlers/valueConsistentBreaker.ftlh" as vC>
<#import "../../modalInputManage/formStatusCleaner.ftlh" as fSc>

<#macro ramEdit id ramModel ramMemory>
    <div class="modal fade" id="ramEditModal${id}" data-backdrop="static" tabindex="-1"
         role="dialog" aria-labelledby="ramEditLabel${id}" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-title h5" id="ramEditLabel${id}">Змінити інформацію про оперативну пам'ять</div>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form method="post" id="ramEditForm${id}" action="/ram/edit/${id}"
                          class="form-group needs-validation w-100" novalidate onsubmit="return validateForm${id}()">
                        <div class="form-row mb-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="model">Назва змінюваної моделі:</label>
                                <input type="text" id="editModelInput${id}" class="form-control" name="editModel" required/>
                                <div class="invalid-feedback">Назва змінюваної моделі задано некоректно!</div>
                            </div>
                        </div>
                        <div class="form-row my-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="memory">Обсяг пам'яті (GiB):</label>
                                <input type="number" id="editMemoryInput${id}" min="1" max="225" class="form-control"
                                       name="editMemory" required/>
                                <div class="invalid-feedback">Обсяг пам'яті задано некоректно!</div>
                            </div>
                        </div>
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    </form>
                    <@eB.emptyBreaker inputIds=["editModelInput${id}"]/>
                    <@vC.valueBreaker id="${id}" inputIds=["editModelInput${id}"]/>
                    <@fSc.fieldsStatusCleaner modalId="ramEditModal${id}" formId="ramEditForm${id}"/>

                    <!-- Restore initial value on opening of modal -->
                    <script>
                        $('#ramEditModal${id}').on('show.bs.modal', function () {
                            document.getElementById("editModelInput${id}").value = "${ramModel}";
                            document.getElementById("editMemoryInput${id}").value = "${ramMemory}";
                        })
                    </script>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-dark" data-dismiss="modal">Скасувати</button>
                    <button type="submit" form="ramEditForm${id}" class="btn btn-danger">Змінити</button>
                </div>
            </div>
        </div>
    </div>
</#macro>