SELECT *
FROM (
SELECT 	a.idschedeanalisi,
		a.scheda || decode(b.NUMERORICHIESTA,NULL,'','/'||b.NUMERORICHIESTA) AS scheda,
		a.identifier,
		a.DATARICHIESTA,
		a.cliente,
		a.procedura,
		a.PERSONALE,
		a.assegnatore,
		a.numallegati,
		a.CONVALIDADATAORA,
		decode(DBMS_LOB.substr(b.DESCRIZIONE, 4000),NULL,a.testo,DBMS_LOB.substr(b.DESCRIZIONE, 4000))  as testo
from
(
select 	sa.progressivo as idschedeanalisi,
		sa.NUMEROSCHEDA as scheda,
		sa.NUMEROSCHEDA as identifier,
		sa.DATARICHIESTA,
		(select c.DESCCLIENTE from ELENCOCLIENTI c where c.IDCLIENTE = sa.IDCLIENTE) as cliente,
		(select pr.PROCEDURA from PROCEDUR pr where pr.IDPROCEDURA = sa.IDPROCEDURA) as procedura,
		p.PERSONALE,
		(select p2.personale from personale p2 where p2.idpersonale =ans.idassegnatore) as assegnatore,
		DBMS_LOB.substr(sa.NOTE, 4000)  as testo,
		(select count(*) from allegati al where al.IDSCHEDAANALISI = sa.progressivo) as numallegati,
		ans.CONVALIDADATAORA,
		ans.IDRICHIESTA
from 	SCHEDEANALISI sa,
	 	PERSONALE p,
	 	ANALISISVOLTE ans
where	sa.IDPROGRAMMATORE = p.IDPERSONALE(+)
and 	ans.IDSCHEDAANALISI(+) = sa.PROGRESSIVO
and 	ans.CONVALIDADATAORA is null
)a,
richiesteclienti B 
WHERE 	a.idrichiesta = b.IDRICHIESTECLIENTE(+)
ORDER BY a.scheda desc, b.NUMERORICHIESTA desc
/****/
)
WHERE rownum<100

