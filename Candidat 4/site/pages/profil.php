<div class="centerho" style="top: 225px;">
    <h1>KELLIS LE LOUËR</h1><br>
    <h3>INSCRIT DEPUIS LE 12 DECEMBRE 2019</h3>
</div>


<table class="table table-bordered">
	<tr>
		<th>Pseudo</th>
		<th>Date</th>
		<th>Source</th>
		<th>Durée</th>
		<th>Raison</th>
	</tr>
	<tr>
		<td title="<?php echo"comment va tu"; ?>"></td>
		<td><?= $element->created ?></td>
		<td><?= $Chat->formattage($element->source); ?></td>
		<td><?= $element->expires ?></td>
		<td><?= $element->reason ?></td>
	</tr>
</table>