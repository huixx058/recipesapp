import { element, by, ElementFinder } from 'protractor';

export class RecipeeComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-recipee div table .btn-danger'));
    title = element.all(by.css('jhi-recipee div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getText();
    }
}

export class RecipeeUpdatePage {
    pageTitle = element(by.id('jhi-recipee-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nameInput = element(by.id('field_name'));
    descriptionInput = element(by.id('field_description'));
    stepsInput = element(by.id('field_steps'));
    ingredientSelect = element(by.id('field_ingredient'));
    mealTypeSelect = element(by.id('field_mealType'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setNameInput(name) {
        await this.nameInput.sendKeys(name);
    }

    async getNameInput() {
        return this.nameInput.getAttribute('value');
    }

    async setDescriptionInput(description) {
        await this.descriptionInput.sendKeys(description);
    }

    async getDescriptionInput() {
        return this.descriptionInput.getAttribute('value');
    }

    async setStepsInput(steps) {
        await this.stepsInput.sendKeys(steps);
    }

    async getStepsInput() {
        return this.stepsInput.getAttribute('value');
    }

    async ingredientSelectLastOption() {
        await this.ingredientSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async ingredientSelectOption(option) {
        await this.ingredientSelect.sendKeys(option);
    }

    getIngredientSelect(): ElementFinder {
        return this.ingredientSelect;
    }

    async getIngredientSelectedOption() {
        return this.ingredientSelect.element(by.css('option:checked')).getText();
    }

    async mealTypeSelectLastOption() {
        await this.mealTypeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async mealTypeSelectOption(option) {
        await this.mealTypeSelect.sendKeys(option);
    }

    getMealTypeSelect(): ElementFinder {
        return this.mealTypeSelect;
    }

    async getMealTypeSelectedOption() {
        return this.mealTypeSelect.element(by.css('option:checked')).getText();
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class RecipeeDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-recipee-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-recipee'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
