<div class="centerho" style="top: 225px;">
    <h1>KELLIS LE LOUËR</h1><br>
    <h3>INSCRIT DEPUIS LE 12 DECEMBRE 2019</h3>



	<table class="table table-bordered">
		<tr>
			<th>NOM</th>
			<th>ID</th>
			<th>DATE</th>

		</tr>
		<?php
		$reqvrc = $bdd->prepare("SELECT * FROM vrc WHERE user_id = ? ORDER BY date DESC");
		$reqvrc->execute(array($_GET['id']));
		$vrc = $reqvrc->fetchAll();
		$vrccount = $reqvrc->rowCount();

		for($i=0;$i < $vrccount;$i++) {
			echo'
			<tr>
				<td>'.$vrc[$i]['name'].'</td>
				<td>'.$vrc[$i]['digit'].'</td>
				<td>'.date('d/m/y H:i' ,$vrc[$i]['date']).'</td>
			</tr>';
		}
		?>
	</table>
</div>