import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProvaComponentsPage, ProvaDeleteDialog, ProvaUpdatePage } from './prova.page-object';

const expect = chai.expect;

describe('Prova e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let provaComponentsPage: ProvaComponentsPage;
  let provaUpdatePage: ProvaUpdatePage;
  let provaDeleteDialog: ProvaDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Prova', async () => {
    await navBarPage.goToEntity('prova');
    provaComponentsPage = new ProvaComponentsPage();
    await browser.wait(ec.visibilityOf(provaComponentsPage.title), 5000);
    expect(await provaComponentsPage.getTitle()).to.eq('escolaApp.prova.home.title');
    await browser.wait(ec.or(ec.visibilityOf(provaComponentsPage.entities), ec.visibilityOf(provaComponentsPage.noResult)), 1000);
  });

  it('should load create Prova page', async () => {
    await provaComponentsPage.clickOnCreateButton();
    provaUpdatePage = new ProvaUpdatePage();
    expect(await provaUpdatePage.getPageTitle()).to.eq('escolaApp.prova.home.createOrEditLabel');
    await provaUpdatePage.cancel();
  });

  it('should create and save Prova', async () => {
    const nbButtonsBeforeCreate = await provaComponentsPage.countDeleteButtons();

    await provaComponentsPage.clickOnCreateButton();

    await promise.all([
      provaUpdatePage.setNomeInput('nome'),
      provaUpdatePage.setNumquestoesInput('5'),
      provaUpdatePage.setEnunciadoInput('enunciado'),
      provaUpdatePage.setTextoInput('texto'),
    ]);

    expect(await provaUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
    expect(await provaUpdatePage.getNumquestoesInput()).to.eq('5', 'Expected numquestoes value to be equals to 5');
    expect(await provaUpdatePage.getEnunciadoInput()).to.eq('enunciado', 'Expected Enunciado value to be equals to enunciado');
    expect(await provaUpdatePage.getTextoInput()).to.eq('texto', 'Expected Texto value to be equals to texto');

    await provaUpdatePage.save();
    expect(await provaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await provaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Prova', async () => {
    const nbButtonsBeforeDelete = await provaComponentsPage.countDeleteButtons();
    await provaComponentsPage.clickOnLastDeleteButton();

    provaDeleteDialog = new ProvaDeleteDialog();
    expect(await provaDeleteDialog.getDialogTitle()).to.eq('escolaApp.prova.delete.question');
    await provaDeleteDialog.clickOnConfirmButton();

    expect(await provaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
