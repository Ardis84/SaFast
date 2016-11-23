SELECT *
FROM (
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
		ans.CONVALIDADATAORA
from 	SCHEDEANALISI sa,
	 	PERSONALE p,
	 	ANALISISVOLTE ans
where	sa.IDPROGRAMMATORE = p.IDPERSONALE(+)
and 	ans.IDSCHEDAANALISI(+) = sa.PROGRESSIVO
and 	ans.CONVALIDADATAORA is null
/****/
order by sa.NUMEROSCHEDA desc
)
WHERE rownum<100

